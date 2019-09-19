package com.java.butterfly.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.java.butterfly.system.entity.SysUserResources;

public interface SysUserResourcesMapper {
    
    /**
     * 
     * TODO：    获取用户资源
     * @author xulu    
     * @date: 2017年4月26日 下午5:26:33
     * @Title: loadUserResources    
     * @param userId
     * @return List<SysUserResources> 返回值
     */
    List<SysUserResources> loadUserResources(@Param(value = "userId") String userId);
    
}