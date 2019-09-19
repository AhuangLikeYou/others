package com.java.butterfly.system.service;

import java.util.Map;

import com.java.butterfly.common.dto.ResultMsg;
import com.java.butterfly.system.dto.ResourceDTO;
import com.java.butterfly.system.entity.SysResourceInfo;

public interface IResourceInfoService {
    Map getResourceByPage(ResourceDTO resourceDTO);
    
    ResultMsg addResource(SysResourceInfo resourceInfo);
    
    ResultMsg deleteByIds(String ids);
    
    ResultMsg updateStatus(String id);
    
    SysResourceInfo getById(String id);
    
    ResultMsg updateDetails(SysResourceInfo resourceInfo);
    
}
