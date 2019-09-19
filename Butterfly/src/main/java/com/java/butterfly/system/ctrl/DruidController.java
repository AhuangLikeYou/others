package com.java.butterfly.system.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/system/druid")
public class DruidController {
    
    private static Logger logger = Logger.getLogger(DruidController.class);
    
    @RequestMapping(value = "/druidPage")
    public ModelAndView logout(HttpServletRequest request) {
        return new ModelAndView("views/system/druidControl");
    }
}
