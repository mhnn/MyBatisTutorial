package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Subject;
import com.atguigu.mybatis.mapper.SubjectMapperDynamic;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DynamicSqlTest {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
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


    }
}
