package com.java.butterfly.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.java.butterfly.system.entity.SysRoleResources;

public interface SysRoleResourcesMapper {
    int insert(SysRoleResources record);
    
    int insertSelective(SysRoleResources record);
    
    int deleteConnect(@Param(value = "roleId") String roleId, @Param(value = "resourcesId") List resourcesId);
}