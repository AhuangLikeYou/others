package com.java.butterfly.common.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * TODO    shiro工具类
 * @ClassName: ShiroUtil    
 * @author xulu    
 * @date: 2017年4月26日 上午10:54:27    
 * @version  v 1.0
 */
public class ShiroUtil {
    
    /**
     * TODO：    得到用户凭证，根据配置此方法执行后
     * @author xulu    
     * @date: 2017年4月26日 上午10:56:08
     * @Title: login    
     * @param userName
     * @param password void 返回值
     */
    public static void login(String userName, String password) {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            return;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        currentUser.login(token);
        currentUser.hasRole("");
    }
    
    /**
     * TODO：登出    
     * @author xulu    
     * @date: 2017年4月26日 上午11:14:04
     * @Title: logout     void 返回值
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }
    
    /**
     * TODO：    获取当前登录用户的SHIRO中的session
     * @author xulu    
     * @date: 2017年4月26日 上午11:14:35
     * @Title: getSession    
     * @return Session 返回值
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }
}
