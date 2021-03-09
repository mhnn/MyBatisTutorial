package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Admin;
import com.atguigu.mybatis.mapper.AdminMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException{
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
    /**
     * 1.根据xml配置文件(全局配置文件)创建一个SqlSessionFactory对象
     *      有数据源一些运行环境信息
     * 2.sql映射文件;配置了每一个sql,以及sql的封装规则等
     * 3.将sql映射文件注册在全局配置文件中
     * 4.写代码:
     *      1)根据全局配置文件的到SqlSessionFactory
     *      2)使用sqlSession工厂,获取到sqlSession对象使用他来执行增删改查
     *          一个sqlSession就是代表和数据库的一次会话,用完关闭
     *      3)使用sql的唯一标志来告诉MyBatis执行哪个sql.sql都是保存在sql映射文件中的
     * @throws IOException
     */
    @Test
    public void selectById() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //2.获取sqlSession实例,能直接执行已映射的sql语句
        //selectOne参数:
        //  1.唯一标识符
        //  2.parameter,要执行sql的用到的参数
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            Admin admin = openSession.selectOne("com.atguigu.mybatis.mapper.AdminMapper.getAdminById", 1);
            System.out.println(admin);
        }finally {
            openSession.close();
        }
    }

    /**
     * 接口式编程
     * 原生   Dao ====>  DaoImpl
     * mybatis   Mapper ===> xxMapper.xml
     *
     * sqlSession代表和数据库的一次会话,用完必须关闭
     * sqlSession和connection一样,都是非线程安全的.故每次使用都应该去获取新的对象
     * mapper接口无实现类,但mybatis会为接口生成一个代理对象.
     *      (将接口和xml进行绑定)
     *      AdminMapper adminMapper = sqlSession.getMapper(AdminMapper.class)
     * 两个重要的配置文件:
     *      mybatis的全局配置文件;包含数据库连接池信息,事务管理器信息等..系统运行环境信息(但可以没有,通过其他方式提供)
     *      sql映射文件:保存了每一个sql语句的映射信息;
     *                  将sql抽取出来.
     * @throws IOException
     */
    @Test
    public void selectById01() throws IOException{

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        SqlSession openSession = sqlSessionFactory.openSession();
        try {
        //3.获取接口的实现类对象
        //会为接口自动的创建一个代理对象,代理对象去执行增删改查方法
        AdminMapper mapper = openSession.getMapper(AdminMapper.class);
        Admin admin = mapper.getAdminById(1);
        System.out.println(admin);
        }finally {
            openSession.close();
        }

    }
    @Test
    /**
     * mybatis允许增删改直接定义以下类型返回值
     *  Integer,Long,Boolean
     *  切记手动提交数据
     */
    public void test03() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取到的openSession不自动提交
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            AdminMapper adminMapper = openSession.getMapper(AdminMapper.class);
//            Map<String,Object> map = new HashMap<>();
//            map.put("aId", 1);
//            map.put("aName", "root");
//            Admin admin = adminMapper.getAdminByMap(map);
                        Admin admin = adminMapper.getAdminByIdAndName(1, "root");
            System.out.println(admin);
            //测试添加
//            Admin admin = new Admin(null, "jerry01", "123");
//            adminMapper.addAdmin(admin);
//            System.out.println(admin.getaId());
            //测试修改
//            adminMapper.updateAdmin(new Admin(2, "yang", "123"));
            //测试删除
//            boolean deleteAdmin = adminMapper.deleteAdmin(2);
//            System.out.println(deleteAdmin);
            //手动提交
            openSession.commit();
        }finally {
            openSession.close();
        }
    }
    @Test
    public void test04() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取到的openSession不自动提交
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            AdminMapper adminMapper = openSession.getMapper(AdminMapper.class);
//            List<Admin> adminList = adminMapper.getAdminByNameLike("%e%");
//            for (Admin admin: adminList){
//                System.out.println(admin);
//            }
            Map<Integer, Admin> admin = adminMapper.getAdminByNameLikeReturnMap("%e%");
            System.out.println(admin);
//            openSession.commit();
        }finally {
            openSession.close();
        }
    }
    @Test
    public void testFirstLevelCache() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取到的openSession不自动提交
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            AdminMapper adminMapper = openSession.getMapper(AdminMapper.class);
            Admin admin = adminMapper.getAdminById(4);
            System.out.println(admin);
            //再次执行一遍
            Admin admin1 = adminMapper.getAdminById(4);
            System.out.println(admin1);
            /**
             * ==>  Preparing: select * from tbl_admin WHERE a_id = ?
             * ==> Parameters: 4(Integer)
             * <==    Columns: a_id, a_name, a_password
             * <==        Row: 4, jerry01, 123
             * <==      Total: 1
             * Admin{aId=4, aName='jerry01', aPassword='123'}
             * Admin{aId=4, aName='jerry01', aPassword='123'}
             */
            System.out.println(admin == admin1);    //true

//            openSession.commit();
        }finally {
            openSession.close();
        }
    }
    @Test
    public void testSecondLevelCache() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //第一个session
        SqlSession openSession = sqlSessionFactory.openSession();
        //第二个session
        SqlSession openSession1 = sqlSessionFactory.openSession();
        try {

            AdminMapper adminMapper = openSession.getMapper(AdminMapper.class);
            AdminMapper adminMapper1 = openSession1.getMapper(AdminMapper.class);
            Admin admin = adminMapper.getAdminById(4);
            System.out.println(admin);
            openSession.close();
            Admin admin1 = adminMapper1.getAdminById(4);
            System.out.println(admin1);
            openSession1.close();
        }finally {
//            openSession.close();
        }
        }
}