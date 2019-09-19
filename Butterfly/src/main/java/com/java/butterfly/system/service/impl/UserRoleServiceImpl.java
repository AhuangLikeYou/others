package com.java.butterfly.system.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.java.butterfly.common.dto.CommonConstant;
import com.java.butterfly.common.dto.ResultMsg;
import com.java.butterfly.common.util.EmptyUtils;
import com.java.butterfly.system.dao.SysUserRoleMapper;
import com.java.butterfly.system.entity.SysUserRole;
import com.java.butterfly.system.service.IUserRoleService;

/**
 * Created by lu.xu on 2017/6/30.
 * TODO:
 */
@Service
public class UserRoleServiceImpl implements IUserRoleService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);
    
    @Resource
    private SysUserRoleMapper userRoleMapper;
    
    @Override
    public ResultMsg userRoleConnect(String roleId, String userIdIds) {
        if (EmptyUtils.isEmpty(roleId, userIdIds)) {
            return new ResultMsg(false, CommonConstant.PARAMETER_ERROR);
        }
        
        List usersId = Arrays.asList(userIdIds.split(","));
        this.userRoleMapper.deleteConnect(roleId, usersId);
        
        String[] usersArr = userIdIds.split(",");
        for (String userId : usersArr) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRole.setCreateDate(new Date());
            this.userRoleMapper.insertSelective(userRole);
        }
        return new ResultMsg(true, CommonConstant.SUCCESS);
    }
}
