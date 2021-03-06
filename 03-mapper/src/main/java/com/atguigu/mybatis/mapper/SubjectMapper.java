package com.atguigu.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubjectMapper {

    Integer updateSubjectType(@Param("sId") Integer sId, @Param("typeId") Integer typeId);

    List<Integer> selectSubjectId();

    Integer updateSubjectRate(@Param("sId") Integer sId,@Param("sRate") Float sRate);

    Integer updateHit(@Param("sId") Integer sId,@Param("sHit")Integer sHit);
}
