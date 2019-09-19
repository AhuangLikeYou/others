package com.java.butterfly.business.test.dao;

import com.java.butterfly.business.test.entity.TempTest;

public interface TempTestMapper {
    int deleteByPrimaryKey(String testId);
    
    int insert(TempTest record);
    
    int insertSelective(TempTest record);
    
    TempTest selectByPrimaryKey(String testId);
    
    int updateByPrimaryKeySelective(TempTest record);
    
    int updateByPrimaryKey(TempTest record);
    
    int selectAllDataCount();
}