package com.java.butterfly.system.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.butterfly.system.service.IUserRoleService;

/**
 * 用户角色信息维护
 */
@Controller
@RequestMapping(value = "/system/userRole")
public class UserRoleController {
    private static Logger logger = Logger.getLogger(UserRoleController.class);
    
    @Resource
    private IUserRoleService userRoleService;
    
    @RequestMapping(value = "/userRoleConnectPage")
    public ModelAndView userRoleConnectPage(HttpServletRequest request) {
        request.setAttribute("roleId", request.getParameter("roleId"));
        return new ModelAndView("views/system/userRoelConnect");
    }
    
    @RequestMapping(value = "/userRoleConnect")
    @ResponseBody
    public Object userRoleConnect(HttpServletRequest request) {
        return this.userRoleService.userRoleConnect(request.getParameter("roleId"), request.getParameter("userIds"));
    }
    
}
