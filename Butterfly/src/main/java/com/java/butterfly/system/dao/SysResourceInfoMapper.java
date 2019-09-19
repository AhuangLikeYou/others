package com.java.butterfly.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.java.butterfly.system.dto.ResourceDTO;
import com.java.butterfly.system.entity.SysResourceInfo;

public interface SysResourceInfoMapper {
    int deleteByPrimaryKey(String resourceId);
    
    int insert(SysResourceInfo record);
    
    int insertSelective(SysResourceInfo record);
    
    SysResourceInfo selectByPrimaryKey(String resourceId);
    
    int updateByPrimaryKeySelective(SysResourceInfo record);
    
    int updateByPrimaryKey(SysResourceInfo record);
    
    List<SysResourceInfo> getListByPage(ResourceDTO resourceDTO);
    
    int getListByPageCount(@Param(value = "keyWords") String keyWords);
    
    int deleteByPrimaryKeys(List ids);
    
    List<SysResourceInfo> selectByExample(SysResourceInfo resourceInfo);
    
}