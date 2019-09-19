package com.java.butterfly.system.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户、角色、权限 情况概览
 */
@Controller
@RequestMapping(value = "/system/userRoleResource")
public class UserRoleResourceController {
    private static Logger logger = Logger.getLogger(UserRoleResourceController.class);
    
    //    @Resource
    //    private IUserRoleService userRoleService;
    
    @RequestMapping(value = "/view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("views/system/userRoleResourceDetail");
    }
    
    @RequestMapping(value = "/queryUser")
    @ResponseBody
    public Object queryUser(HttpServletRequest request) {
        return null;
    }
    
    @RequestMapping(value = "/queryRole")
    @ResponseBody
    public Object queryRole(HttpServletRequest request) {
        return null;
    }
    
    @RequestMapping(value = "/queryResource")
    @ResponseBody
    public Object queryResource(HttpServletRequest request) {
        return null;
    }
}
