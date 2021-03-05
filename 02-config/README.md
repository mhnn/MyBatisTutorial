# settings标签

settings标签中有许多mybatis的设置属性

详情:[Click Here](https://mybatis.org/mybatis-3/zh/configuration.html#settings)

可以根据需要设置

# typeAliases标签

可以为bean类起别名,此后在sql映射文件中就可以直接使用别名来设置resultType/paramType等属性值

**二级标签:**

* typeAlias
  * type:指定全类名
  * alias:指定别名(可不填,默认为类名小写)

```xml
<typeAliases>
	<typeAlias type="com.atguigu.mybatis.bean.Admin" alias="admin"/>
</typeAliases>
```

* package
  * name:指定包名

```xml
<typeAliases>
	<package name="com.atguigu.mybatis.bean"/>
</typeAliases>
```

**但别名本身使用不区分大小写**

**拓展:**

**注解起别名**

​	直接在bean类上方使用`@Alias("xxx")`即可快速起别名,并且可以覆盖config中的别名配置

**Java数据类型别名**

​	mybatis为java数据类型默认起好了别名

| 别名      | 映射的类型 | 别名       | 映射的类型 |
| :-------- | :--------- | ---------- | ---------- |
| _byte     | byte       | byte       | Byte       |
| _long     | long       | long       | Long       |
| _short    | short      | short      | Short      |
| _int      | int        | int        | Integer    |
| _integer  | int        | integer    | Integer    |
| _double   | double     | double     | Double     |
| _float    | float      | float      | Float      |
| _boolean  | boolean    | boolean    | Boolean    |
| string    | String     | decimal    | BigDecimal |
| date      | Date       | bigdecimal | BigDecimal |
| object    | Object     | map        | Map        |
| hashmap   | HashMap    | list       | List       |
| arraylist | ArrayList  | collection | Collection |
| iterator  | Iterator   |            |            |

# plugins标签

插件标签,学习完原理后再来深入了解

MyBatis允许程序猿拦截到映射的语句的核心步骤.具体有四大对象的执行方法:

* Executor/执行器(update,query...)
* ParameterHandler/参数处理器(getParameterObject,setParameters)
* ResultSetHandler/结果集处理器,将执行后获得的结果集封装成JavaBean对象
* StatementHandler/语句处理器

可以在这些方法执行的前后进行拦截(动态代理)

# enviroments

> 主要用于配置事务管理器和数据源,整合spring后淘汰

配置环境

* environment:具体的环境信息
  * transactionManager:事务管理器
  * dataSource:数据源

# databaseIdProvider

**数据库厂商标识**

MyBatis 可以根据不同的数据库厂商执行不同的语句，这种多厂商的支持是基于映射语句中的 `databaseId` 属性.

# mappers

将sql映射文件注册到全局配置中

```xml
<mappers>
    <mapper resource="mapping/AdminMapper.xml"/>
    <!--注册接口
	1.有sql映射文件,xml与接口类同名,且在同一目录
	主要用于基于注解的Mapper接口映射
	-->
    <mapper class="com.atguigu.mybatis.AdminMapper" />
</mappers>
```

**批量注册**

需要xml与接口类同名且在同一个包下

```xml
<mappers>
	<package name="com.atguigu.mybatis.mapper" />
</mappers>
```

# 补充

配置文件的标签有先后顺序,查看configuration的dtd约束即可看到,顺序如下:

```
properties?, settings?, typeAliases?, typeHandlers?, objectFactory?, objectWrapperFactory?, reflectorFactory?, plugins?, environments?, databaseIdProvider?, mappers?
```

