package com.java.butterfly.system.dto;

import com.java.butterfly.common.dto.PageInfo;

// TODO: Auto-generated Javadoc
/**
 * TODO(这里用一句话描述这个类的作用).
 *用户信息分页查询参数
 * @author xulu
 * @version  v 1.0
 * @ClassName: UserInfoDTO
 * @date: 2017-5-4 16:08:42
 */
public class UserInfoDTO extends PageInfo {
    
    /** The key words. */
    private String keyWords;
    
    public String getKeyWords() {
        return keyWords;
    }
    
    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
    
}
