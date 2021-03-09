package com.atguigu.mybatis.mapper;

import com.atguigu.mybatis.bean.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapperDynamic {
    void addAds(@Param("ads") List<Admin> ads);

    List<Admin> getAdsTestInnerParam(Admin admin);
}
