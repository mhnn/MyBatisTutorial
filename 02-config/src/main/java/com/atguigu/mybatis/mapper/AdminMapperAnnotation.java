package com.atguigu.mybatis.mapper;

import com.atguigu.mybatis.bean.Admin;
import org.apache.ibatis.annotations.Select;

/**
 * 基于注解的mapper映射
 */
public interface AdminMapperAnnotation {
    @Select("Select * from tbl_admin where a_id= #{aId}")
     Admin getAdminById(Integer aId);
}
