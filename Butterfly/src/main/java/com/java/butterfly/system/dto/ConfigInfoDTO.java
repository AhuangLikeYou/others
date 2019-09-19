package com.java.butterfly.system.dto;

import com.java.butterfly.common.dto.PageInfo;

/**
 * Created by lu.xu on 2017/7/14.
 * TODO:
 */
public class ConfigInfoDTO extends PageInfo {
    private String type;
    
    private String isCache;
    
    private String key;
    
    private String keyWords;
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getKeyWords() {
        return keyWords;
    }
    
    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
    
    public String getIsCache() {
        return isCache;
    }
    
    public void setIsCache(String isCache) {
        this.isCache = isCache;
    }
}
