package com.java.butterfly.system.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.butterfly.system.dto.RoleInfoDTO;
import com.java.butterfly.system.entity.SysRoleInfo;
import com.java.butterfly.system.service.IRoleInfoService;

@Controller
@RequestMapping(value = "/system/roleInfo")
public class RoleInfoController {
    private static Logger logger = Logger.getLogger(RoleInfoController.class);
    
    @Resource
    private IRoleInfoService roleInfoService;
    
    @RequestMapping(value = "/roleInfoPage")
    public ModelAndView roleInfoPage(HttpServletRequest request) throws Exception {
        return new ModelAndView("views/system/roleInfo");
    }
    
    @ResponseBody
    @RequestMapping(value = "/getRoleList")
    public Object getRoleList(HttpServletRequest request, RoleInfoDTO roleInfoDTO) throws Exception {
        return this.roleInfoService.getRoleListByPage(roleInfoDTO);
    }
    
    @RequestMapping(value = "/addRolePage")
    public ModelAndView addRolePage(HttpServletRequest request, SysRoleInfo roleInfo) throws Exception {
        return new ModelAndView("views/system/roleInfoAdd");
    }
    
    @ResponseBody
    @RequestMapping(value = "/addRole")
    public Object addRole(HttpServletRequest request, SysRoleInfo roleInfo) throws Exception {
        return this.roleInfoService.addRole(roleInfo);
    }
    
    @ResponseBody
    @RequestMapping(value = "/deleteRole")
    public Object deleteRole(HttpServletRequest request) throws Exception {
        return this.roleInfoService.deleteByIds(request.getParameter("rolesId"));
    }
    
    @ResponseBody
    @RequestMapping(value = "/updateRoleStatus")
    public Object updateRoleStatus(HttpServletRequest request) throws Exception {
        return this.roleInfoService.updateStatus(request.getParameter("roleId"));
    }
    
    @RequestMapping(value = "/roleInfoDetail")
    public ModelAndView roleInfoDetail(HttpServletRequest request) throws Exception {
        SysRoleInfo roleInfo = this.roleInfoService.getById(request.getParameter("roleId"));
        request.setAttribute("roleInfo", roleInfo);
        return new ModelAndView("views/system/roleInfoDetail");
    }
    
    @ResponseBody
    @RequestMapping(value = "/updateRoleDetail")
    public Object updateRoleDetail(HttpServletRequest request, SysRoleInfo roleInfo) throws Exception {
        return this.roleInfoService.updateDetails(roleInfo);
    }
    
}
