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
import com.java.butterfly.system.dao.SysRoleResourcesMapper;
import com.java.butterfly.system.entity.SysRoleResources;
import com.java.butterfly.system.service.IRoleResourcesService;

@Service
public class RoleResourcesServiceImpl implements IRoleResourcesService {
    
    private static final Logger logger = LoggerFactory.getLogger(RoleResourcesServiceImpl.class);
    
    @Resource
    private SysRoleResourcesMapper roleResourcesMapper;
    
    @Override
    public ResultMsg roleResourceConnect(String roleId, String resourcesId) {
        if (EmptyUtils.isEmpty(roleId, resourcesId)) {
            return new ResultMsg(false, CommonConstant.PARAMETER_ERROR);
        }
        
        List resourcesIdList = Arrays.asList(resourcesId.split(","));
        this.roleResourcesMapper.deleteConnect(roleId, resourcesIdList);
        
        String[] resourcesArr = resourcesId.split(",");
        for (String resourceId : resourcesArr) {
            SysRoleResources roleResources = new SysRoleResources();
            roleResources.setRoleId(roleId);
            roleResources.setResourceId(resourceId);
            roleResources.setCreateDate(new Date());
            this.roleResourcesMapper.insertSelective(roleResources);
        }
        return new ResultMsg(true, CommonConstant.SUCCESS);
    }
}
