package com.java.butterfly.system.enums;

/**
 * Created by lu.xu on 2017/6/2.
 * TODO:
 */
public enum SysUserRoleStatusEnums {
    //code nameCn remarks
    UNKNOWN(null, "未知状态", "未知状态"), USER_SATUS_NORMAL("0", "启用", "用户状态"), USER_SATUS_LOCK("1", "锁定",
        "用户状态"), ROLE_SATUS_NORMAL("0", "启用", "角色状态"), ROLE_SATUS_LOCK("1", "锁定",
            "角色状态"), RESOURCE_SATUS_NORMAL("0", "启用", "资源状态"), RESOURCE_SATUS_LOCK("1", "锁定", "资源状态");
    
    public static SysUserRoleStatusEnums getType(String key) {
        switch (key) {
            case "userLock":
                return USER_SATUS_LOCK;
            case "userNormal":
                return USER_SATUS_NORMAL;
            case "roleLock":
                return ROLE_SATUS_LOCK;
            case "roleNormal":
                return ROLE_SATUS_NORMAL;
            case "resourcesLock":
                return RESOURCE_SATUS_LOCK;
            case "resourcesNormal":
                return RESOURCE_SATUS_NORMAL;
            default:
                return UNKNOWN;
        }
    }
    
    private final String code;
    
    private final String nameCn;
    
    private final String remarks;
    
    SysUserRoleStatusEnums(String code, String nameCn, String remarks) {
        this.code = code;
        this.nameCn = nameCn;
        this.remarks = remarks;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getNameCn() {
        return nameCn;
    }
    
    public String getRemarks() {
        return remarks;
    }
}
