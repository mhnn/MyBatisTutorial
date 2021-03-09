package com.atguigu.mybatis.mapper;

import com.atguigu.mybatis.bean.Admin;
import org.apache.ibatis.annotations.MapKey;

public interface AdminMapperPlus {
    @MapKey("typeId")
    Admin getAdminById(Integer aId);
}
