package com.java.butterfly.system.entity;

import java.util.Date;

public class SysUserRole {
    private String userId;
    
    private String roleId;
    
    private Date createDate;
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
    
    public String getRoleId() {
        return roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}