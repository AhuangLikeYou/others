package com.java.butterfly.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.java.butterfly.system.dto.ConfigInfoDTO;
import com.java.butterfly.system.entity.SysConfigInfo;

public interface SysConfigInfoMapper {
    int deleteByPrimaryKey(String configId);
    
    int insert(SysConfigInfo record);
    
    int insertSelective(SysConfigInfo record);
    
    SysConfigInfo selectByPrimaryKey(String configId);
    
    List<SysConfigInfo> selectByPrimaryKeys(List configIds);
    
    int updateByPrimaryKeySelective(SysConfigInfo record);
    
    int updateByPrimaryKey(SysConfigInfo record);
    
    List<SysConfigInfo> configListByPage(ConfigInfoDTO configInfoDTO);
    
    int configListByPageCount(ConfigInfoDTO configInfoDTO);
    
    List<SysConfigInfo> configListByTypeAndKey(@Param(value = "type") String type, @Param(value = "key") String key);
    
    int deleteByTypeAndKey(@Param(value = "type") String type, @Param(value = "key") String key);
    
    int deleteByPrimaryKeys(List<String> configIds);
}