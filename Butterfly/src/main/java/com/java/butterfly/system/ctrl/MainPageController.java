package com.java.butterfly.system.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.java.butterfly.common.dto.CommonConstant;

/**
 * Created by Administrator on 2017/7/23.
 * TODO:
 */
@RestController
@RequestMapping("/system/userAccess")
public class MainPageController {
    private static Logger logger = Logger.getLogger(SysConfigInfoController.class);
    
    @RequestMapping(value = "/mainPageUnauth")
    public ModelAndView mainPageUnauth(HttpServletRequest request) throws Exception {
        if (null != request.getSession().getAttribute(CommonConstant.USER_INFO)) {
            return this.mainPage(request);
        }
        return new ModelAndView("login");
    }
    
    @RequestMapping(value = "/mainPage")
    public ModelAndView mainPage(HttpServletRequest request) throws Exception {
        return new ModelAndView("views/system/main");
    }
    
}
