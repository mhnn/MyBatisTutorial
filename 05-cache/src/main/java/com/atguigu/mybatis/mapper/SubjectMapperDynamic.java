package com.atguigu.mybatis.mapper;

import com.atguigu.mybatis.bean.Subject;

import java.util.List;

public interface SubjectMapperDynamic {
    //查询一组电影信息，带了哪个字段查询条件就拼上字段的值
    List<Subject> getSubsByConditionIf(Subject subject);

    List<Subject> getSubsByConditionTrim(Subject subject);

    List<Subject> getSubsByConditionChoose(Subject subject);

    void updateSubjectInfo(Subject subject);

    List<Subject> getSubsByConditionForeach(List<Integer> typeIds);
}
