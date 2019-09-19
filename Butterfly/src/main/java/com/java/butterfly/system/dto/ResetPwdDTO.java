package com.java.butterfly.system.dto;

/**
 * Created by lu.xu on 2017/8/9.
 * TODO:重置密码
 */
public class ResetPwdDTO {
    private String userId;
    
    private String pwd;
    
    private String newPwd;
    
    private String newPwdConfirm;
    
    public String getPwd() {
        return pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public String getNewPwd() {
        return newPwd;
    }
    
    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
    
    public String getNewPwdConfirm() {
        return newPwdConfirm;
    }
    
    public void setNewPwdConfirm(String newPwdConfirm) {
        this.newPwdConfirm = newPwdConfirm;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
