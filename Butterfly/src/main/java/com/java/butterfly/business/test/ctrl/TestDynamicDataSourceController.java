package com.java.butterfly.business.test.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.butterfly.business.test.service.ITestDynamicDataSourceService;

/**
 * Created by lu.xu on 2017/7/22.
 * TODO:测试动态数据源
 */
@RestController
@RequestMapping(value = "/system/business/test")
public class TestDynamicDataSourceController {
    private static Logger logger = Logger.getLogger(TestPageController.class);
    
    @Resource
    private ITestDynamicDataSourceService testDynamicDataSourceService;
    
    @RequestMapping(value = "/testDynamicDataSource")
    public Object testDynamicDataSource(HttpServletRequest request) {
        logger.info(">> 测试切换数据源...");
        return this.testDynamicDataSourceService.testDynamicDataSource();
    }
    
    @RequestMapping(value = "/testDynamicDataSourceAddData")
    public Object testDynamicDataSourceAddData(HttpServletRequest request) {
        logger.info(">> 测试切换数据源添加一条数据...");
        return this.testDynamicDataSourceService.addOne();
    }
}
