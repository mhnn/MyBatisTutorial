# # 动态Sql

动态SQL功能可以使我们在映射文件中书写的sql更加灵活智能

通过

* if
* choose
* choose (when, otherwise)
* trim (where, set)
* foreach

来动态地加载我们的SQL语句

## if&OGNL表达式

业务需求：

查询一组电影信息，传入了哪个字段，sql就拼上相应的查询字段

```xml
<!--Mapper.xml-->
<!--查询一组电影信息，带了哪个字段查询条件就带上字段的值-->
    <!--List<Subject> getSubsByConditionIf(Subject subject);-->
    <select id="getSubsByConditionIf" resultType="subject">
        select * from tbl_subject
        where
        <!--test：判断表达式，c:if  test-->
        <if test="sId!=null">
            s_id = #{sId}
        </if>
         <if test="sHit != '' and sHit != null">
               1=1 and s_hit > #{sHit}
         </if>
         <if test="sDate != null and sDate.trim() != ''">
             and s_date like #{sDate}
         </if>
    </select>
```

```java
//Mapper.java
public interface SubjectMapperDynamic {
    //查询一组电影信息，带了哪个字段查询条件就拼上字段的值
    List<Subject> getSubsByConditionIf(Subject subject);
}
```

```java
//测试
@Test
    public void testIf() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            SubjectMapperDynamic mapper = openSession.getMapper(SubjectMapperDynamic.class);
            Subject subject = new Subject();
            subject.setsHit(100);
            subject.setsDate("%2020%");
            List<Subject> subjects = mapper.getSubsByConditionIf(subject);
            System.out.println(subjects);
        }finally {
            openSession.close();
        }
```

# where标签

上例中，若没有带sId，查询时将会多一个and，导致sql语法错误。

where标签正好可以解决这个问题

**将if标签们用`<where></where>`包裹起来**（仅适合放在前面，where只会去掉第一个and）

# trim标签(了解)

* prefix:前缀，trim标签体中是整个字符串拼串后的结果。prefix给拼串后的字符串加前缀
*  prefixOverrides：前缀覆盖。**去掉**整个字符串中多余的字符
* suffix：后缀。
* suffixOverrides：后缀覆盖。本例中拼完后 and，所以这里填and

# choose分支选择

**Ex：如果带了id就用id查，如果带了hit就用hit查，只会进入其中一个**

```xml
<!--  List<Subject> getSubsByConditionChoose(Subject subject);  -->
    <select id="getSubsByConditionChoose" resultType="subject">
        select * from tbl_subject
        <where>
            <choose>
                <when test="sId!=null">
                    s_id = #{sId}
                </when>
                <when test="sHit!=null and sHit != ''">
                    s_hit > #{sHit}
                </when>
                <otherwise>
                    1=1
                </otherwise>
            </choose>
        </where>
    </select>
```

# set

业务场景：

更新电影信息时，传入什么值，更新什么字段的值

**if用来判断传入了哪些字段，就拼上哪些sql；**

**set用来包住if，去除多余的逗号“，”**

```xml
 <!--  void updateSubjectInfo(Subject subject);  -->
    <select id="updateSubjectInfo" resultType="subject">
        update tbl_subject
        <set>
            <if test="sName != null">
                s_name = #{sName},
            </if>
            <if test="sDirector!=null">
                s_director = #{sDirector},
            </if>
            <if test="sActor != null">
                s_actor = #{sActor},
            </if>
            <if test="picSrc!=null">
                pic_src = #{picSrc},
            </if>
            <if test="sRate!=null">
                s_rate = #{sRate},
            </if>
            <if test="sDate!=null">
                s_date = #{sDate},
            </if>
            <if test="typeId!=null">
                type_id = #{typeId},
            </if>
            <if test="sContent!=null">
                s_content = #{sContent},
            </if>
            <if test="sHit!=null">
                s_hit = #{sHit},
            </if>
        </set>
        <where>
            s_id = #{sId}
        </where>
    </select>
```

*trim标签也可以实现上面需求，使用前后缀*

# foreach批量查询

foreach标签可以遍历传进来的参数集合，适用于要查找参数有多个时使用

```xml
<!--   List<Subject> getSubsByConditionForeach(List<Integer> typeIds);  -->
    <select id="getSubsByConditionForeach" resultType="subject">
        select * from tbl_subject
        where type_id in
        <!-- collection:指定要遍历的集合类型（list、map。。。）
         list类型的参数会特殊地处理封装在map中，map的key就是list
         item：将当前遍历出的元素赋值给指定变量
         separator="":每个元素之间的分隔符
         open:遍历完所有结果，在开头拼接一个open指定的值
         close：遍历完所有结果，在结尾拼接一个指定值
         index=""：索引。遍历list的时候是索引，item就是值
                        遍历map的时候index代表map的key，item就是map的值
         -->
        <foreach collection="list" item="item_id" separator=","
        open="(" close=")">
            #{item_id}
        </foreach>
    </select>
```

**注意：若接口的方法参数添加了`@Param("ids")`注解，那么就可以使用`collection="ids"`，如下例**

# foreach批量保存

```xml
<!--  void addAds(@Param("ads") List<Admin> ads);  -->
<insert id="addAds">
    insert into tbl_admin(a_name,a_password)
    values
    <foreach collection="ads" item="admin" separator=","
    >
        (#{admin.aName},#{admin.aPassword})
    </foreach>
</insert>
```

# 内置参数 

不只是方法传递过来的参数可以被用来判断，取值。。。

mybatis默认还有两个内置参数

1. _parameter(常用):代表整个参数
   1. 单个参数：_parameter就是这个参数
   2. 多个参数：被封装为一个map；_parameter代表这个map
2. _databaseId:如果配置了DatabaseIdProvider标签，它就会有值，代表当前数据库的别名

```xml
<!--  List<Admin> getAdsTestInnerParam(Admin admin);  -->
    <select id="getAdsTestInnerParam" resultType="admin">
        select * from tbl_admin
        <if test="_parameter!=null">
            <where>
                a_name = #{aName}
                 <!--a_name = #{_parameter.aName}-->
            </where>
        </if>
    </select>
```

# bind绑定

可以将OGNL表达式的值绑定到一个变量中，方便后来引用

例子：利用bind拼接sql条件的通配符`%`

```xml
<!--  List<Admin> getAdsTestInnerParam(Admin admin);  -->
    <select id="getAdsTestInnerParam" resultType="admin">
        select * from tbl_admin
        <!-- 使用bind拼接字符串 -->
        <bind name="_aName" value="'%'+aName+'%'" />
        <if test="_parameter!=null">
            <where>
                a_name like #{_aName}
                 <!--a_name = #{_parameter.aName}-->
            </where>
        </if>
    </select>
```

# sql标签_抽取可重用的sql片段

**与select同级**

```xml
<insert id="addAds">
        insert into tbl_admin(
    <!-- 使用include来引用sql片段 -->
        <include refid="insertColumn">
    		<property name="testColumn" value="abc" />
    	</include>
        )
        values
        <foreach collection="ads" item="admin" separator=","
        >
            (#{admin.aName},#{admin.aPassword})
        </foreach>
    </insert>
```

```xml
<!-- 使用sql标签来指定要重用的部分 -->
<!-- 
1.抽取经常要查询的列明，或者插入用的列名抽取出来方便引用
2.include来引用已经抽取的sql
3.include中还可以插入property标签，来添加自定义的属性
		${prop}，注意是$
-->
<sql id="insertColumn">
        a_name,a_password
    </sql>
```

