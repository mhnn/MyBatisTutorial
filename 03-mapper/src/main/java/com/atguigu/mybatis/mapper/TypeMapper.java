package com.atguigu.mybatis.mapper;

import com.atguigu.mybatis.bean.Type;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface TypeMapper {
    @MapKey("typeName")
    List<Map<String,Integer>> getTypeInfo();

    Integer selectTypeIdByName(String typeName);
}
