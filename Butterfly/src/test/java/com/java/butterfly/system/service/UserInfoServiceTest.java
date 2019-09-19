package com.java.butterfly.system.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.java.butterfly.common.dto.ResultMsg;
import com.java.butterfly.common.util.BeanUtils;
import com.java.butterfly.system.entity.SysUserInfo;
import com.java.butterfly.system.enums.SysUserRoleStatusEnums;

/**
 * Created by lu.xu on 2017/8/16.
 * TODO: 用户测试
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserInfoServiceTest {
    @Resource
    private IUserInfoService iUserInfoService;
    
    private SysUserInfo baseUser = null;
    
    private SysUserInfo userinfo = null;
    
    @Before
    public void showMsgBefore() throws Exception {
        System.out.println("***********UserInfoServiceTest start**********");
        baseUser = new SysUserInfo();
        baseUser.setUserName("junittestName");
        baseUser.setUserPwd("junittestPwd");
        baseUser.setStatus(SysUserRoleStatusEnums.USER_SATUS_LOCK.getCode());
        baseUser.setRealName("junitRealName");
        baseUser.setUserPhone("185111111151");
        baseUser.setCreateDate(new Date());
        baseUser.setModifyDate(new Date());
        
        userinfo = new SysUserInfo();
        BeanUtils.copyProperties(baseUser, userinfo);
    }
    
    @Test
    public void main() throws Exception {
        /**删除冗余数据*/
        deleteUsers();
        addUser();
        queryListByPage();
        updateUser();
        updateStatus();
        queryByUserName();
        getByUserId();
        resetPwd();
        /**删除测试数据*/
        deleteUsers();
    }
    
    public void queryListByPage() {
        System.out.println("-----queryListByPage start----");
        
        System.out.println("-----queryListByPage end----");
    }
    
    public void deleteUsers() {
        System.out.println("-----deleteUsers start----");
        System.out.println("删除测试数据");
        SysUserInfo user = this.iUserInfoService.queryByUserName(baseUser.getUserName());
        if (null == user) {
            return;
        }
        ResultMsg rmg = this.iUserInfoService.deleteUsers(user.getUserId());
        Assert.assertTrue(rmg.isSuccess());
        System.out.println("-----deleteUsers end----");
    }
    
    public void addUser() throws Exception {
        System.out.println("-----addUser start----");
        BeanUtils.copyProperties(baseUser, userinfo);
        ResultMsg rmg = this.iUserInfoService.addUser(userinfo);
        Assert.assertTrue(rmg.isSuccess());
        System.out.println("-----addUser end----");
    }
    
    public void updateUser() {
        System.out.println("-----updateUser start----");
        System.out.println("-----updateUser end----");
    }
    
    public void updateStatus() {
        System.out.println("-----updateStatus start----");
        System.out.println("-----updateStatus end----");
    }
    
    public void queryByUserName() {
        System.out.println("-----queryByUserName start----");
        System.out.println("-----queryByUserName end----");
    }
    
    public void getByUserId() {
        System.out.println("-----getByUserId start----");
        System.out.println("-----getByUserId end----");
    }
    
    public void resetPwd() {
        System.out.println("-----resetPwd start----");
        System.out.println("-----resetPwd end----");
    }
    
    @After
    public void showMsgAfter() {
        System.out.println("***********UserInfoServiceTest end**********");
    }
}
