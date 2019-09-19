package com.java.butterfly.system.entity;

import java.util.Date;

import com.java.butterfly.common.dto.PageInfo;

public class SysConfigInfo extends PageInfo {
    private String configId;
    
    private String configType;
    
    private String configKey;
    
    private String configValue;
    
    private String remarks;
    
    private String iscache;
    
    private Date modifyDate;
    
    private Date createDate;
    
    public String getConfigId() {
        return configId;
    }
    
    public void setConfigId(String configId) {
        this.configId = configId == null ? null : configId.trim();
    }
    
    public String getConfigType() {
        return configType;
    }
    
    public void setConfigType(String configType) {
        this.configType = configType == null ? null : configType.trim();
    }
    
    public String getConfigKey() {
        return configKey;
    }
    
    public void setConfigKey(String configKey) {
        this.configKey = configKey == null ? null : configKey.trim();
    }
    
    public String getConfigValue() {
        return configValue;
    }
    
    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
    
    public String getIscache() {
        return iscache;
    }
    
    public void setIscache(String iscache) {
        this.iscache = iscache == null ? null : iscache.trim();
    }
    
    public Date getModifyDate() {
        return modifyDate;
    }
    
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public SysConfigInfo(String configId, String configType, String configKey, String configValue, String remarks,
        String iscache, Date modifyDate, Date createDate) {
        this.configId = configId;
        this.configType = configType;
        this.configKey = configKey;
        this.configValue = configValue;
        this.remarks = remarks;
        this.iscache = iscache;
        this.modifyDate = modifyDate;
        this.createDate = createDate;
    }
    
    public SysConfigInfo() {
    }
    
    public SysConfigInfo(String configType, String configKey, String configValue) {
        this.configType = configType;
        this.configKey = configKey;
        this.configValue = configValue;
    }
}