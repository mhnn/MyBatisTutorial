package com.atguigu.mybatis.test;

import com.atguigu.mybatis.mapper.SubjectMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.OpenOption;
import java.util.List;
import java.util.Random;

public class UpdateTest {
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
            SubjectMapper subjectMapper = openSession.getMapper(SubjectMapper.class);
            List<Integer> Subject_list = subjectMapper.selectSubjectId();
            for (Integer sId:Subject_list){

                subjectMapper.updateHit(sId, new Random().nextInt(1000));
            }
            openSession.commit();
        }finally {
            openSession.close();
        }
    }


}
