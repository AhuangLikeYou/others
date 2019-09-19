package com.java.butterfly.system.service;

import com.java.butterfly.common.dto.ResultMsg;

/**
 * 角色资源关联
 */
public interface IRoleResourcesService {
    
    ResultMsg roleResourceConnect(String roleId, String resourcesId);
}
