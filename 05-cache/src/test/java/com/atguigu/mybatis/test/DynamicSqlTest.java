package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Admin;
import com.atguigu.mybatis.bean.Subject;
import com.atguigu.mybatis.mapper.AdminMapper;
import com.atguigu.mybatis.mapper.AdminMapperDynamic;
import com.atguigu.mybatis.mapper.SubjectMapperDynamic;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    @Test
    public void testTrim() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            SubjectMapperDynamic mapper = openSession.getMapper(SubjectMapperDynamic.class);
            Subject subject = new Subject();
            subject.setsHit(100);
            List<Subject> subs = mapper.getSubsByConditionTrim(subject);
            for (Subject subject1:subs){
                System.out.println(subject1);
            }
        } finally {
            openSession.close();
        }
    }
    @Test
    public void testChoose() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            SubjectMapperDynamic mapper = openSession.getMapper(SubjectMapperDynamic.class);
            Subject subject = new Subject();
            List<Subject> subs = mapper.getSubsByConditionChoose(subject);
            for (Subject subject1:subs){
                System.out.println(subject1);
            }
        }finally {
            openSession.close();
        }
        }
    @Test
    public void testSet() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            SubjectMapperDynamic mapper = openSession.getMapper(SubjectMapperDynamic.class);
            Subject subject = new Subject();
            subject.setsId(2609258);
            subject.setsHit(200);
            mapper.updateSubjectInfo(subject);
            openSession.commit();
        }finally {
            openSession.close();
        }
        }
    @Test
    public void testForeach() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            SubjectMapperDynamic mapper = openSession.getMapper(SubjectMapperDynamic.class);
            List<Subject> subs = mapper.getSubsByConditionForeach(Arrays.asList(24, 29, 25));
            for (Subject subject1:subs){
                System.out.println(subject1);
            }

        }finally {
            openSession.close();
        }
        }
    @Test
    public void testForeachAdd() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            AdminMapperDynamic mapperDynamic = openSession.getMapper(AdminMapperDynamic.class);
            List<Admin>  ads = new ArrayList<>();
            ads.add(new Admin("tom1", "atguigu"));
//            ads.add(new Admin("frank", "12311"));
            mapperDynamic.addAds(ads);
            openSession.commit();
        }finally {
            openSession.close();
        }
    }
    @Test
    public void testInnerParam() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            AdminMapperDynamic mapperDynamic = openSession.getMapper(AdminMapperDynamic.class);
            Admin admin = new Admin();
            admin.setaName("e");
            List<Admin> admins = mapperDynamic.getAdsTestInnerParam(admin);
            System.out.println(admins);
        }finally {
            openSession.close();
        }
    }
}
