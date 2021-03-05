package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Admin;
import com.atguigu.mybatis.mapper.AdminMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MapperPlusTest {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
    @Test
    public void test() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            AdminMapperPlus adminMapper = openSession.getMapper(AdminMapperPlus.class);
            Admin admin = adminMapper.getAdminById(1);
            System.out.println(admin);
        }finally {
            openSession.close();
        }


    }
}
