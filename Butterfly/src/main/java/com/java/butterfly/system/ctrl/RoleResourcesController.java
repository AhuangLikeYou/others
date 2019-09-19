package com.java.butterfly.system.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.butterfly.system.service.IRoleResourcesService;

@Controller
@RequestMapping(value = "/system/roleResources")
public class RoleResourcesController {
    private static Logger logger = Logger.getLogger(RoleResourcesController.class);
    
    @Resource
    private IRoleResourcesService roleResourcesService;
    
    @RequestMapping(value = "/roleResourceConnectPage")
    public ModelAndView roleResourceConnectPage(HttpServletRequest request) {
        request.setAttribute("roleId", request.getParameter("roleId"));
        return new ModelAndView("views/system/roleResourceConnect");
    }
    
    @RequestMapping(value = "/roleResuorceConnect")
    @ResponseBody
    public Object roleResuorceConnect(HttpServletRequest request) {
        return this.roleResourcesService
            .roleResourceConnect(request.getParameter("roleId"), request.getParameter("resourceIds"));
    }
}
