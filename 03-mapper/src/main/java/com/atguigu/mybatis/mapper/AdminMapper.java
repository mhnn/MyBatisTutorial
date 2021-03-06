package com.atguigu.mybatis.mapper;

import com.atguigu.mybatis.bean.Admin;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminMapper {
    //多条记录封装一个map,Map<Integer,Employee>:键是记录封装后的javaBean
    //告诉mybatis使用这个属性作为key封装map
    @MapKey("aId")
    Map<Integer,Admin> getAdminByNameLikeReturnMap(String aName);

    Map<String,Object> getAdminByIdReturnMap(Integer aId);

    List<Admin> getAdminByNameLike(String aName);

    Admin getAdminByMap(Map<String,Object> map);
    /**
     * 根据id和name查询
     * @param id
     * @param name
     * @return
     */
    Admin getAdminByIdAndName(@Param("aId") Integer id, @Param("aName") String name);
    /**
     * 根据Id找管理员
     * @param aId
     * @return
     */
    Admin getAdminById(Integer aId);

    /**
     * 添加一个管理员
     * @param admin
     */
    void addAdmin(Admin admin);

    /**
     * 修改一个管理员信息
     * @param admin
     */
    void updateAdmin(Admin admin);

    /**
     * 根据id删除一个管理员信息
     * @param id
     */
    boolean deleteAdmin(Integer id);
}
