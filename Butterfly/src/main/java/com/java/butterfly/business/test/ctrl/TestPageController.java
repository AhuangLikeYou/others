package com.java.butterfly.business.test.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lu.xu on 2017/7/22.
 * TODO:可视化测试系统功能界面
 */
@Controller
@RequestMapping("/system/business/test")
public class TestPageController {
    private static Logger logger = Logger.getLogger(TestPageController.class);
    
    @RequestMapping(value = "/testPage")
    public ModelAndView testPage(HttpServletRequest request) {
        return new ModelAndView("views/business/test/testPage");
    }
}
