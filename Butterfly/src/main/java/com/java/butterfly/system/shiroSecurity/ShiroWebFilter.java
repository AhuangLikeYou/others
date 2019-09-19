package com.java.butterfly.system.shiroSecurity;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.java.butterfly.common.util.HttpRequestUtil;

/**
 * Created by lu.xu on 2017/6/7.
 * TODO:custom web filter
 */

public class ShiroWebFilter extends AccessControlFilter {
    private static Logger logger = Logger.getLogger(ShiroWebFilter.class);
    
    private static final String unauthorizedUrl = "/unauthor.html";
    
    private static final String unauthorizedMsg = "你没有权限访问!";
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
        throws Exception {
        //        logger.info(">>>>>>>>>>>isAccessAllowed...");
        if (SecurityUtils.getSubject().isAuthenticated()) {
            //返回true则不经过onAccessDenied
            return true;
        }
        logger.info(" >>ShiroWebFilter,用户访问会话认证未通过，用户未登录 >> ");
        boolean isAjaxRequest = HttpRequestUtil.isAjaxRequest(request);
        if (isAjaxRequest) {
            response.setContentType("text/plain; charset=utf-8");
            response.getWriter().write(unauthorizedMsg);
        } else {
            WebUtils.issueRedirect(request, response, unauthorizedUrl);
        }
        //交给onAccessDenied
        return false;
    }
    
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //        logger.info(">>>>>>>>>>>onAccessDenied...");
        /**
         *交给此方法的请求，皆视为isAccessAllowed不被允许的请求，统一返回false
         * 注意此处如果改为true，则通过连接访问可以跳转无权限界面，但是ajax则不能拦截 
         */
        return false;
    }
}
