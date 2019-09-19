package com.java.butterfly.system.service;

import java.util.Map;

import com.java.butterfly.common.dto.ResultMsg;
import com.java.butterfly.system.dto.RoleInfoDTO;
import com.java.butterfly.system.entity.SysRoleInfo;

public interface IRoleInfoService {
    
    Map getRoleListByPage(RoleInfoDTO roleInfoDTO);
    
    ResultMsg deleteByIds(String ids);
    
    ResultMsg updateDetails(SysRoleInfo roleInfo);
    
    ResultMsg addRole(SysRoleInfo roleInfo);
    
    ResultMsg updateStatus(String id);
    
    SysRoleInfo getById(String id);
    
}
