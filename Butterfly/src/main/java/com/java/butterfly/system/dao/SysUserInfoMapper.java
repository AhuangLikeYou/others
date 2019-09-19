package com.java.butterfly.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.java.butterfly.system.dto.UserInfoDTO;
import com.java.butterfly.system.entity.SysUserInfo;

public interface SysUserInfoMapper {
    
    int deleteByPrimaryKey(String userId);
    
    int insert(SysUserInfo record);
    
    int insertSelective(SysUserInfo record);
    
    SysUserInfo selectByPrimaryKey(String userId);
    
    int updateByPrimaryKeySelective(SysUserInfo record);
    
    int updateByPrimaryKey(SysUserInfo record);
    
    /**
     * 多行删除
     * @param userIds
     * @return
     */
    int deleteByPrimaryKeys(List userIds);
    
    /**
     * 根据用户名查询唯一用户
     * @param userName
     * @return
     */
    SysUserInfo queryByUserName(@Param(value = "userName") String userName);
    
    /**
     * 根据对象（属性）查询
     * @param record
     * @return
     */
    List<SysUserInfo> selectByExample(SysUserInfo record);
    
    /**
     * 列表分页查询
     * @param userinfoDTO
     * @return
     */
    List<SysUserInfo> queryListByPage(UserInfoDTO userinfoDTO);
    
    /**
     * 分页查询求总
     * @param keyWords
     * @return
     */
    int queryListByPageCount(@Param(value = "keyWords") String keyWords);
    
}