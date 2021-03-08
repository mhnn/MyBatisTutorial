package com.atguigu.mybatis.mapper;

import com.atguigu.mybatis.bean.Type;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface TypeMapper {
    @MapKey("typeName")
    List<Map<String,Integer>> getTypeInfo();

    String getTypeById(Integer typeId);

    Integer selectTypeIdByName(String typeName);

    Type getTypeByIdPlus(Integer typeId);

    Type getTypeIdByNameByStep(String type_name);
}
