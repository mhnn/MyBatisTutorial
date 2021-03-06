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

