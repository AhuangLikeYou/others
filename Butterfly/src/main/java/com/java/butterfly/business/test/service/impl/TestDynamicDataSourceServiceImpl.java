package com.java.butterfly.business.test.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.java.butterfly.business.test.dao.TempTestMapper;
import com.java.butterfly.business.test.entity.TempTest;
import com.java.butterfly.business.test.service.ITestDynamicDataSourceService;
import com.java.butterfly.common.service.dynamicDataSource.DataSource;
import com.java.butterfly.common.util.IdGeneratorUtils;

/**
 * Created by lu.xu on 2017/7/22.
 * TODO:测试多数据源
 */
@Service
//切换数据源
@DataSource("dataSourceForOracle")
public class TestDynamicDataSourceServiceImpl implements ITestDynamicDataSourceService {
    
    private static Logger logger = Logger.getLogger(TestDynamicDataSourceServiceImpl.class);
    
    @Resource
    private TempTestMapper tempTestMapper;
    
    @Override
    public Object testDynamicDataSource() {
        return this.tempTestMapper.selectAllDataCount();
    }
    
    @Override
    public Object addOne() {
        TempTest test = new TempTest();
        String id = IdGeneratorUtils.getSerialNo();
        test.setTestId(id);
        test.setTestName("test");
        test.setTestAge(Long.valueOf(100));
        this.tempTestMapper.insertSelective(test);
        return this.tempTestMapper.selectByPrimaryKey(id);
    }
}
