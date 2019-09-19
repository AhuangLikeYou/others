package com.java.butterfly.system.service;

import com.java.butterfly.common.dto.ResultMsg;

/**
 * Created by lu.xu on 2017/6/30.
 * TODO:用户角色关联
 */
public interface IUserRoleService {
    
    ResultMsg userRoleConnect(String roleId, String userIds);
}
