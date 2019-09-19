package com.java.butterfly.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.java.butterfly.system.dto.RoleInfoDTO;
import com.java.butterfly.system.entity.SysRoleInfo;

public interface SysRoleInfoMapper {
    int deleteByPrimaryKey(String roleId);
    
    int deleteByPrimaryKeys(List userIds);
    
    int insert(SysRoleInfo record);
    
    int insertSelective(SysRoleInfo record);
    
    SysRoleInfo selectByPrimaryKey(String roleId);
    
    int updateByPrimaryKeySelective(SysRoleInfo record);
    
    int updateByPrimaryKey(SysRoleInfo record);
    
    List<SysRoleInfo> getRoleListByPage(RoleInfoDTO roleInfoDTO);
    
    int getRoleListByPageCount(@Param(value = "keyWords") String keyWords);
    
    /**
     * 根据用户id获取所有角色
     * @param userId
     * @return
     */
    List<SysRoleInfo> getAllRolesByUserId(@Param(value = "userId") String userId);
    
}