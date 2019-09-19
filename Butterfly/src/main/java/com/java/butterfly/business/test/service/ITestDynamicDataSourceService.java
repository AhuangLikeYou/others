package com.java.butterfly.business.test.service;

/**
 * Created by lu.xu on 2017/7/22.
 * TODO:测试多数据源
 */
public interface ITestDynamicDataSourceService {
    /**
     * 动态数据源获取所有
     * @return
     */
    public Object testDynamicDataSource();
    
    /**
     * 动态数据源添加数据
     * @return
     */
    public Object addOne();
}
