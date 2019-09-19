package com.java.butterfly.common.dto;

/**
 * Created by lu.xu on 2017/6/19.
 * TODO: used in SystemInitService
 * 定义系统启动后执行方法
 */
public class SystemInitBeansDTO {
    //类名（全路径）
    private String beanName;
    
    //方法名
    private String initMethod;
    
    public String getBeanName() {
        return beanName;
    }
    
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    
    public String getInitMethod() {
        return initMethod;
    }
    
    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }
}
