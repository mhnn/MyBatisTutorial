package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Admin;
import com.atguigu.mybatis.bean.Subject;
import com.atguigu.mybatis.bean.Type;
import com.atguigu.mybatis.mapper.AdminMapperPlus;
import com.atguigu.mybatis.mapper.SubjectMapper;
import com.atguigu.mybatis.mapper.TypeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperPlusTest {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            AdminMapperPlus adminMapper = openSession.getMapper(AdminMapperPlus.class);
            Admin admin = adminMapper.getAdminById(1);
            System.out.println(admin);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {

            TypeMapper typeMapper = openSession.getMapper(TypeMapper.class);

            //准备对Subject表更新
            SubjectMapper subjectMapper = openSession.getMapper(SubjectMapper.class);
            //获取csv文件的数据
            String splitBy = ",";
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Administrator.USER-20201125AL\\Desktop\\filmInfo.csv"));
            String line = br.readLine();
            Map<Integer, String> csvMap = new HashMap<Integer, String>();
            while ((line = br.readLine()) != null) {
                String[] b = line.split(splitBy);
                csvMap.put(Integer.parseInt(b[0]), b[5]);
            }
            br.close();

            //遍历csv表数据
            for (Map.Entry<Integer, String> entry : csvMap.entrySet()) {
                String mapValue = entry.getValue();
                Integer mapKey = entry.getKey();
                Integer typeId = typeMapper.selectTypeIdByName(mapValue);
                Integer rows = subjectMapper.updateSubjectType(mapKey, typeId);
                System.out.println("更新成功，影响" + rows + "行");
                //更新Subject表
                //subjectMapper.updateSubjectType(mapKey, map.get(i).get(mapValue));
            }
            openSession.commit();
        } finally {
            openSession.close();
        }
    }

    @Test
    public void testSubjectAndType() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            SubjectMapper subjectMapper = openSession.getMapper(SubjectMapper.class);
            Subject subjectAndType = subjectMapper.getSubjectAndType(2609258);
            System.out.println(subjectAndType);
            System.out.println(subjectAndType.getType());
        } finally {
            openSession.close();
        }
    }

    @Test
    public void testSubjectByStep() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            SubjectMapper subjectMapper = openSession.getMapper(SubjectMapper.class);
            Subject subjectByStep = subjectMapper.getSubjectByStep(2609258);
            System.out.println(subjectByStep.getPicSrc());
            System.out.println(subjectByStep.getType());
        } finally {
            openSession.close();
        }
    }

    @Test
    public void testgetTypeByIdPlus() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            TypeMapper typeMapper = openSession.getMapper(TypeMapper.class);
            Type type = typeMapper.getTypeByIdPlus(24);
            System.out.println(type);
            System.out.println(type.getSubs());

        } finally {
            openSession.close();
        }
    }

    @Test
    public void test06() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            TypeMapper typeMapper = openSession.getMapper(TypeMapper.class);
            Type type = typeMapper.getTypeIdByNameByStep("剧情");
            System.out.println(type);
           // System.out.println(type.getSubs());
        }finally {
            openSession.close();
        }
        }
}
