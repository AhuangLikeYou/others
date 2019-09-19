package com.java.butterfly.system.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.java.butterfly.system.dto.ConfigInfoDTO;
import com.java.butterfly.system.entity.SysConfigInfo;
import com.java.butterfly.system.service.ISysConfigInfoService;

/**
 *系统配置操作类
 */
@RestController
@RequestMapping(value = "/system/sysConfig")
public class SysConfigInfoController {
    private static Logger logger = Logger.getLogger(SysConfigInfoController.class);
    
    @Resource
    private ISysConfigInfoService sysConfigInfoService;
    
    /**
     * 跳转进入系统配置入口
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sysconfigPage")
    public ModelAndView sysconfigPage(HttpServletRequest request) throws Exception {
        request.setAttribute("type", request.getParameter("type"));
        request.setAttribute("key", request.getParameter("key"));
        request.setAttribute("keyWords", request.getParameter("keyWords"));
        return new ModelAndView("views/system/sysConfig");
    }
    
    @RequestMapping(value = "/sysconfigAddPage")
    public ModelAndView sysconfigAddPage(HttpServletRequest request) throws Exception {
        return new ModelAndView("views/system/sysConfigAdd");
    }
    
    @RequestMapping(value = "/sysconfigDetailPage")
    public ModelAndView sysconfigDetailPage(HttpServletRequest request) throws Exception {
        request.setAttribute("sysConfig", this.sysConfigInfoService.getById(request.getParameter("configId")));
        return new ModelAndView("views/system/sysConfigDetail");
    }
    
    @RequestMapping(value = "/getConfigList")
    public Object getConfigList(HttpServletRequest request, ConfigInfoDTO configInfoDTO) throws Exception {
        return this.sysConfigInfoService.getConfigList(configInfoDTO);
    }
    
    @RequestMapping(value = "/addConfig")
    public Object addConfig(HttpServletRequest request, SysConfigInfo sysConfigInfo) throws Exception {
        return this.sysConfigInfoService.addConfig(sysConfigInfo);
    }
    
    @RequestMapping(value = "/deleteConfig")
    public Object deleteConfig(HttpServletRequest request) throws Exception {
        return this.sysConfigInfoService.deleteConfig(request.getParameter("configId"));
    }
    
    @RequestMapping(value = "/updateConfig")
    public Object updateConfig(HttpServletRequest request, SysConfigInfo sysConfigInfo) throws Exception {
        return this.sysConfigInfoService.updateConfig(sysConfigInfo);
    }
    
}
