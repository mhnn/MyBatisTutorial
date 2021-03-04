# MyBatis01-HelloWorld
**目录结构**
```
├─src
│  ├─main
│  │  ├─java
│  │  │  │  MybatisEx.java
│  │  │  │  
│  │  │  └─com
│  │  │      └─atguigu
│  │  │          └─mybatis
│  │  │              ├─bean
│  │  │              │      Admin.java
│  │  │              │      
│  │  │              └─mapper
│  │  │                      AdminMapper.java
│  │  │                      
│  │  └─resources
│  │      │  logback.xml
│  │      │  mybatis-config.xml
│  │      │  
│  │      └─mapping
│  │              AdminMapper.xml
│  │              
│  └─test
│      └─java
│          └─com
│              └─atguigu
│                  └─mybatis
│                      └─beanTest
│                              MyBatisTest.java
```
本节课认识了MyBatis的基本运行流程
* 重要文件1:mybatis配置文件,用于声明连接池,声明事务管理,注册每一个sql映射文件
* sql映射文件保存了将要运行的sql语句,返回值,参数等信息
* SqlSessionFactory通过读取mybatis全局配置文件来生成
* SqlSessionFactory生成SqlSession
* 每一个SqlSession都是一次和数据库的会话
* 在每次会话完毕后,都要关闭SqlSession

**现在流行的方式是通过一个Mapper接口类,来与sql映射文件动态绑定;一个sql映射文件对应一个Mapper接口类**
