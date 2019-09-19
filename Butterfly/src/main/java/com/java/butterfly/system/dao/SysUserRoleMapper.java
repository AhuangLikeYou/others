package com.java.butterfly.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.java.butterfly.system.entity.SysUserRole;

public interface SysUserRoleMapper {
    int insert(SysUserRole record);
    
    int insertSelective(SysUserRole record);
    
    int deleteByUserId(@Param(value = "userId") String userId);
    
    int deleteByRoleId(@Param(value = "roleId") String roleId);
    
    int deleteConnect(@Param(value = "roleId") String roleId, @Param(value = "usersId") List usersId);
}