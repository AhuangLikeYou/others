package com.java.butterfly.system.enums;

/**
 * Created by lu.xu on 2017/6/2.
 * TODO:
 */
public enum SysConfigEnums {
    IS_CACHE_Y("Y", "加入缓存"), IS_CACHE_N("N", "不加入缓存");
    
    private final String code;
    
    private final String nameCn;
    
    SysConfigEnums(String code, String nameCn) {
        this.code = code;
        this.nameCn = nameCn;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getNameCn() {
        return nameCn;
    }
}
