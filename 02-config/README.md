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

| 别名       | 映射的类型 |
| :--------- | :--------- |
| _byte      | byte       |
| _long      | long       |
| _short     | short      |
| _int       | int        |
| _integer   | int        |
| _double    | double     |
| _float     | float      |
| _boolean   | boolean    |
| string     | String     |
| byte       | Byte       |
| long       | Long       |
| short      | Short      |
| int        | Integer    |
| integer    | Integer    |
| double     | Double     |
| float      | Float      |
| boolean    | Boolean    |
| date       | Date       |
| decimal    | BigDecimal |
| bigdecimal | BigDecimal |
| object     | Object     |
| map        | Map        |
| hashmap    | HashMap    |
| list       | List       |
| arraylist  | ArrayList  |
| collection | Collection |
| iterator   | Iterator   |