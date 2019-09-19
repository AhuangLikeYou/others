package com.java.butterfly.system.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.butterfly.system.dto.ResourceDTO;
import com.java.butterfly.system.entity.SysResourceInfo;
import com.java.butterfly.system.service.IResourceInfoService;

@Controller
@RequestMapping(value = "/system/resourceInfo")
public class ResourceInfoController {
    
    private static Logger logger = Logger.getLogger(ResourceInfoController.class);
    
    @Resource
    private IResourceInfoService resourcesInfoService;
    
    @RequestMapping(value = "/resourcePage")
    public ModelAndView resourceInfo(HttpServletRequest request) throws Exception {
        return new ModelAndView("views/system/resourceInfo");
    }
    
    @ResponseBody
    @RequestMapping(value = "/getResourceList")
    public Object getResourceList(HttpServletRequest request, ResourceDTO resourceDTO) throws Exception {
        return this.resourcesInfoService.getResourceByPage(resourceDTO);
    }
    
    @RequestMapping(value = "/addResourcePage")
    public ModelAndView addResourcePage(HttpServletRequest request) throws Exception {
        return new ModelAndView("views/system/resourceAdd");
    }
    
    @ResponseBody
    @RequestMapping(value = "/addResource")
    public Object addResource(HttpServletRequest request, SysResourceInfo sysResourceInfo) throws Exception {
        return this.resourcesInfoService.addResource(sysResourceInfo);
    }
    
    @ResponseBody
    @RequestMapping(value = "/deleteResource")
    public Object deleteResource(HttpServletRequest request) throws Exception {
        return this.resourcesInfoService.deleteByIds(request.getParameter("resourceIds"));
    }
    
    @ResponseBody
    @RequestMapping(value = "/updateResourceStatus")
    public Object updateResourceStatus(HttpServletRequest request) throws Exception {
        return this.resourcesInfoService.updateStatus(request.getParameter("resourceId"));
    }
    
    @RequestMapping(value = "/ResourceInfoDetail")
    public ModelAndView ResourceInfoDetail(HttpServletRequest request) throws Exception {
        SysResourceInfo resourceInfo = this.resourcesInfoService.getById(request.getParameter("resourceId"));
        request.setAttribute("resourceInfo", resourceInfo);
        return new ModelAndView("views/system/resourceDetail");
    }
    
    @ResponseBody
    @RequestMapping(value = "/updateResourceDetail")
    public Object updateResourceDetail(HttpServletRequest request, SysResourceInfo resourceInfo) throws Exception {
        return this.resourcesInfoService.updateDetails(resourceInfo);
    }
    
}
