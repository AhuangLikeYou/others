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
import com.java.butterfly.system.dto.ResetPwdDTO;
import com.java.butterfly.system.entity.SysUserInfo;
import com.java.butterfly.system.enums.SysUserRoleStatusEnums;

/**
 * Created by lu.xu on 2017/8/10.
 * TODO: 用户登录测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserLoginServiceImplTest {
    @Resource
    private IUserLoginService iUserLoginService;
    
    @Resource
    private IUserInfoService iUserInfoService;
    
    private SysUserInfo baseUser = null;
    
    private SysUserInfo userinfo = null;
    
    @Before
    public void showMsgBefore() throws Exception {
        System.out.println("***********UserLoginServiceImplTest start**********");
        
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
        this.loginCheck();
        this.resetPwd();
    }
    
    public void loginCheck() throws Exception {
        System.out.println("-----loginCheck start----");
        System.out.println("清理数据>>");
        SysUserInfo user = this.iUserInfoService.queryByUserName(baseUser.getUserName());
        if (null != user) {
            System.out.println("清理数据>> userid:" + user.getUserId());
            this.iUserInfoService.deleteUsers(user.getUserId());
        }
        
        ResultMsg rmg;
        rmg = iUserInfoService.addUser(userinfo);
        Assert.assertTrue(rmg.isSuccess());
        
        System.out.println("登录锁定用户");
        rmg = iUserLoginService.loginCheck(baseUser.getUserName(), baseUser.getUserPwd());
        Assert.assertFalse(rmg.isSuccess());
        
        System.out.println("更改状态为正常");
        userinfo.setUserPwd(null);
        userinfo.setStatus(SysUserRoleStatusEnums.RESOURCE_SATUS_NORMAL.getCode());
        iUserInfoService.updateUser(userinfo);
        
        rmg = iUserLoginService.loginCheck(baseUser.getUserName(), baseUser.getUserPwd());
        Assert.assertTrue(rmg.isSuccess());
        
        System.out.println("测试完毕 删除数据");
        user = this.iUserInfoService.queryByUserName(baseUser.getUserName());
        if (null != user) {
            System.out.println("清理数据>> userid:" + user.getUserId());
            this.iUserInfoService.deleteUsers(user.getUserId());
        }
        System.out.println("-----loginCheck end----");
        
    }
    
    public void resetPwd() throws Exception {
        System.out.println("-----resetPwd start----");
        System.out.println("清理数据>>");
        SysUserInfo user = this.iUserInfoService.queryByUserName(baseUser.getUserName());
        if (null != user) {
            System.out.println("清理数据>> userid:" + user.getUserId());
            this.iUserInfoService.deleteUsers(user.getUserId());
        }
        
        ResultMsg rmg;
        BeanUtils.copyProperties(baseUser, userinfo);
        userinfo.setStatus(SysUserRoleStatusEnums.USER_SATUS_NORMAL.getCode());
        rmg = iUserInfoService.addUser(userinfo);
        Assert.assertTrue(rmg.isSuccess());
        user = this.iUserInfoService.queryByUserName(baseUser.getUserName());
        
        ResetPwdDTO dto = new ResetPwdDTO();
        rmg = iUserLoginService.resetPwd(null);
        Assert.assertFalse(rmg.isSuccess());
        
        rmg = iUserLoginService.resetPwd(dto);
        Assert.assertFalse(rmg.isSuccess());
        
        dto.setUserId(user.getUserId());
        rmg = iUserLoginService.resetPwd(dto);
        Assert.assertFalse(rmg.isSuccess());
        
        dto.setPwd(baseUser.getUserPwd());
        rmg = iUserLoginService.resetPwd(dto);
        Assert.assertFalse(rmg.isSuccess());
        
        dto.setNewPwd("123456");
        rmg = iUserLoginService.resetPwd(dto);
        Assert.assertFalse(rmg.isSuccess());
        
        dto.setNewPwdConfirm("123456" + "故意输错");
        rmg = iUserLoginService.resetPwd(dto);
        Assert.assertFalse(rmg.isSuccess());
        
        dto.setNewPwdConfirm("123456");
        dto.setPwd(baseUser.getUserPwd() + "故意输错");
        rmg = iUserLoginService.resetPwd(dto);
        Assert.assertFalse(rmg.isSuccess());
        
        dto.setPwd(baseUser.getUserPwd());
        System.out.println(">>>>>>>>>>>>>>");
        System.out.println(">>>>>>>>>>>>>>");
        System.out.println(dto.getPwd());
        rmg = iUserLoginService.resetPwd(dto);
        Assert.assertTrue(rmg.isSuccess());
        
        System.out.println("测试更改密码，重新登录");
        rmg = iUserLoginService.loginCheck(baseUser.getUserName(), "123456");
        Assert.assertTrue(rmg.isSuccess());
        
        System.out.println("测试完毕 删除数据");
        user = this.iUserInfoService.queryByUserName(baseUser.getUserName());
        if (null != user) {
            System.out.println("清理数据>> userid:" + user.getUserId());
            this.iUserInfoService.deleteUsers(user.getUserId());
        }
        System.out.println("-----resetPwd end----");
    }
    
    @After
    public void showMsgAfter() {
        System.out.println("***********UserLoginServiceImplTest end**********");
    }
}
