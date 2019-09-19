package com.java.butterfly.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.java.butterfly.system.dao.SysUserResourcesMapper;
import com.java.butterfly.system.entity.SysUserInfo;
import com.java.butterfly.system.entity.SysUserResources;
import com.java.butterfly.system.service.IUserResourcesService;

@Service
public class UserResourcesServiceImpl implements IUserResourcesService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserResourcesServiceImpl.class);
    
    @Resource
    private SysUserResourcesMapper sysUserResourcesMapper;
    
    @Override
    public List<SysUserResources> loadUserResources(SysUserInfo userinfo) {
        return this.sysUserResourcesMapper.loadUserResources(userinfo.getUserId());
    }
    
}
