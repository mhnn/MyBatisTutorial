# 增删改功能

* 映射接口类的方法可以设定返回值(Integer/Long/boolean)用以判断增删改的执行影响行数

* 映射文件还支持获取自增主键的值,使用`useGeneratedKeys="true" keyProperty="aId"`

可以在sql语句执行完毕后获得这条元数据的id值

# 参数处理

**单个参数:**mybatis不会做特殊处理,写啥都行

**多个参数:**特殊处理

​		多个参数会被封装成一个map

​		key:param1...paramN,或者参数索引

​		value:传入参数的值

​		#{}就是从map中获取指定key的值;

**命名参数:(推荐)**

​		在接口类的方法的每个参数指定`@param("xx")`注解

​		在sql映射中就可以使用`#{xx}`来取参数值

**POJO:(推荐)**

**如果多个参数正好是我们业务逻辑的数据模型,可以直接传入pojo**

​		#{属性名}:取出传入的pojo的属性值

**map:(推荐)**

**如果多个参数不是业务模型的属性,没有对应的pojo,不常使用可以传入map**

​		#{key}:取出map中对应的值

**TO:(推荐)**

**若多个参数不是业务模型中的数据,但经常要用,推荐编写一个TO(Transfer Object)**

## 思考

```java
public Admin getAdminById(List<Integer> ids);
```

**特别注意:**如果是Collection(List or Set)或数组,也会特殊处理

​		会把传入的list或数组封装在map中

​		key:Collection(collection),如果是List还可以使用list

​				数组(array)

取值: 取出第一个:#{list[0]}

## 封装map过程(源码)

我们知道,如果接口类的方法有多个参数,mybatis将会进行特殊处理,将参数封装成map,具体过程如下:

1. mybatis生成一个代理对象,来执行映射接口类的方法
2. 代理对象实例化了动态代理,接收了method
3. 如果method是Object中的method,就放行;否则就在**后面**调用
4. 到ParamNameResolver类中(处理参数的类,将map构造出来)
5. 在其构造方法中将names赋值
   1. 构造方法中,通过对method的反射,获取注解的值,将值赋值给names
   2. 如果没有注解,就使用索引
6. 调用getNamedParams
   1. 只有一个元素,且无注解,就直接返回
   2. 多个元素或注解,names集合的value作为key;names集合的key又作为取值的参考
   3. 额外的,也会将每一个参数保存到map中,使用新的key:param1...paramN
   4. 至此就可以通过#{id}或者#{param1}来取值了

# 参数值获取

**#号与$的区别**

#{}:预编译形式;防止sql注入

${}:会直接将值拼装在sql语句中

对于原生jdbc不支持占位符的地方我们就可以使用${}来进行取值,比如分表,排序

# #取值时指定参数相关规则

对#{}取值时进行其他规定操作

```
mode
jdbcType
jdbcTypeName
resultMap
ResultMap
```

---

# resultType

返回值类型

-别名或者全类名.若返回的是集合,定义集合中元素的类型

# resultMap(重要)

自定义结果集,自己定义Admin中每一个属性对应结果集的哪一个列

可以在mapper中定义

```xml
<!--  自定义某个JavaBean的封装规则
           type:自定义规则的java类型
            id:方便引用
     -->
    <resultMap id="myAdmin" type="com.atguigu.mybatis.bean.Admin">
        <!--指定主键列
            column:指定哪一列
            property:对应哪个属性
        -->
        <id column="a_id" property="aId"/>
        <result column="a_name" property="aName"/>
        <!--其他不指定的列会自动封装;推荐还是全写-->
        <result column="a_password" property="aPassword" />
    </resultMap>
```

# resultMap关联查询

**业务场景**

```java
public class Subject {
    private Integer sId;
    private String sName;
    private String sDirector;
    private String sActor;
    private String picSrc;
    private Float sRate;
    private String sDate;
    private Type type;  //tbl_type表的映射实体类
    private String sContent;
    private Integer sHit;
    ...
```

**业务要求**

查出电影信息的同时，也查出电影对应的类型名称

```java
//接口类#方法
Subject getSubjectAndType(Integer sId);
```

```xml
<!--映射文件-->
<!--使用级联属性封装结果集-->
    <resultMap id="MyDifSub" type="com.atguigu.mybatis.bean.Subject">
        <id column="s_id" property="sId" />
        <result column="s_name" property="sName" />
        <result column="s_director" property="sDirector" />
        <result column="s_actor" property="sActor" />
        <result column="pic_src" property="picSrc" />
        <result column="s_rate" property="sRate" />
        <result column="s_date" property="sDate" />
        <!--使用级联属性封装结果集-->
        <result column="type_id" property="type.typeId" />
        <result column="type_name" property="type.typeName" />
        
        <result column="s_content" property="sContent" />
        <result column="s_hit" property="sHit" />
    </resultMap>

<select id="getSubjectAndType" resultMap="MyDifSub">
        select s_id,s_name,s_director,s_actor,pic_src,s_rate,s_date,a.type_id,b.type_name,s_content,s_hit
        from tbl_subject a,tbl_type b
        where a.type_id = b.type_id and a.s_id = #{sId}
    </select>
```

**使用association指定联合的javaBean对象**

```xml
<resultMap id="MyDifSub" type="com.atguigu.mybatis.bean.Subject">
        <id column="s_id" property="sId" />
        <result column="s_name" property="sName" />
        <result column="s_director" property="sDirector" />
        <result column="s_actor" property="sActor" />
        <result column="pic_src" property="picSrc" />
        <result column="s_rate" property="sRate" />
        <result column="s_date" property="sDate" />
        <result column="s_content" property="sContent" />
        <result column="s_hit" property="sHit" />
        <!--使用association封装结果集-->
        <association property="type" javaType="com.atguigu.mybatis.bean.Type">
            <id property="typeId" column="type_id" />
            <result column="type_name" property="typeName" />
        </association>
    </resultMap>
```

**使用association进行分步查询**

业务场景：

事先就有根据电影类型id查电影名，又因为有根据电影id查电影信息（其中包括电影类型id），所以借助这两个查询功能即可实现上边的业务需求

```java
//TypeMapper.java#方法
Integer selectTypeIdByName(String typeName);
```

```java
//SubjectMapper.java#方法
Subject getSubjectByStep(Integer sId);
```

```xml
<!--SubjectMapper.xml-->
<resultMap id="MySubjectBySetp" type="com.atguigu.mybatis.bean.Subject">
        <id column="s_id" property="sId" />
        <result column="s_name" property="sName" />
        <result column="s_director" property="sDirector" />
        <result column="s_actor" property="sActor" />
        <result column="pic_src" property="picSrc" />
        <result column="s_rate" property="sRate" />
        <result column="s_date" property="sDate" />
        <result column="s_content" property="sContent" />
        <result column="s_hit" property="sHit" />
        <!--使用association封装结果集
            select:表明当前这个属性是既调用select指定的方法查出来的
            column:指定将哪一列的值传给这个方法,查出对象并封装给property指定的属性
        -->
        <association property="type" select="com.atguigu.mybatis.mapper.TypeMapper.getTypeById"
        column="type_id">
            <id property="typeId" column="type_id" />
            <result column="type_name" property="typeName" />
        </association>
    </resultMap>
<!--Subject getSubjectByStep(Integer sId);-->
    <select id="getSubjectByStep" resultMap="MySubjectBySetp">
        select * from tbl_subject
        where s_id = #{sId}
    </select>
```

**延迟加载**

业务场景：

目前每次查电影信息时，都会自动地把电影类型查出来

我们希望在类型名需要时再查询

需要对分布查询再做配置

```xml
<!--mybatis-config.xml-->
...
<!--当开启时，所有关联对象都会延迟加载。 -->
<setting name="lazyLoadingEnabled" value="true"/>
<!--开启时，任一方法的调用都会加载该对象的所有延迟加载属性。 否则，每个延迟加载属性会按需加载-->
<setting name="aggressiveLazyLoading" value="false"/>
...
```



**Collection定义关联集合封装查询**

业务场景：

查询类型名时将这个类型对应的所有电影信息查询出来

并封装到一个List当中

```xml
<!--bean中带有List，需要封装，使用Collection来封装集合-->
<resultMap id="MyType" type="com.atguigu.mybatis.bean.Type">
        <id property="typeId" column="type_id" />
        <result column="type_name" property="typeName" />
        <!--collection定义关联集合类型的属性封装规则
            ofType:指定集合元素的类型（此处是Subject）
            property:指定要关联的集合对应哪个属性
        -->
        <collection property="subs" ofType="com.atguigu.mybatis.bean.Subject">
            <!--定义这个集合中元素的封装规则-->
            <id column="s_id" property="sId" />
            <result column="s_name" property="sName" />
            <result column="s_director" property="sDirector" />
            <result column="s_actor" property="sActor" />
            <result column="pic_src" property="picSrc" />
            <result column="s_rate" property="sRate" />
            <result column="s_date" property="sDate" />
            <result column="s_content" property="sContent" />
            <result column="s_hit" property="sHit" />
        </collection>
    </resultMap>
```

**Collection之分布查询&懒加载**

```java
//Type实体
public class Type {
    private Integer typeId;
    private String typeName;
    private List<Subject> subs;
    ...
```

```java
//TypeMapper接口
..
Type getTypeIdByNameByStep(String type_name);
..
```

```xml
<!--TypeMapper映射-->
    <resultMap id="MyTypeStep" type="com.atguigu.mybatis.bean.Type">
        <id column="type_id" property="typeId" />
        <result column="type_name" property="typeName" />
        <!--collection中也有select属性-->
        <collection property="subs" select="com.atguigu.mybatis.mapper.SubjectMapper.getSubjectByTypeName"
        column="type_id"></collection>
    </resultMap>
    <!--Type getTypeIdByNameByStep(String type_name);-->
    <select id="getTypeIdByNameByStep" resultMap="MyTypeStep">
        select * from tbl_type
        where type_name = #{typeName}
    </select>
```

```java
//SubjectMapper接口类
...
List<Subject> getSubjectByTypeName(Integer typeId);
    ...
```

```xml
<!--SubjectMapper映射-->
    <!--List<Subject> getSubjectByTypeName(String typeName);-->
    <select id="getSubjectByTypeName" resultType="com.atguigu.mybatis.bean.Subject">
        select * from tbl_subject
        where type_id = #{typeId}
    </select>
```

流程：

TypeMapper调用返回Type类型结果集，然后用结果集中的type_id到SubjectMapper查询对应的电影们，并封装到List当中

**扩展：分步查询查询多列值&fetchType**

1. 上例中的分步查询都仅传递了一个id、type_id，当业务需要传递多列值时，

可以将多列值封装map进行传递，

column="{key=colum1,key2=column2}"

```xml
...
<collection property="subs" select="com.atguigu.mybatis.mapper.SubjectMapper.getSubjectByTypeName"
        column="{typeId=type_id}"></collection>
...
```

2. collection/association中有**fetchType="lazy"**

表示使用延迟加载/懒加载

​	-lazy：延迟

​	-eager：立即

3. discriminator鉴别器

鉴别器：mybatis可以使用其判断某列的值，然后根据某列的值改变封装行为

