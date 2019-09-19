package com.java.butterfly.system.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.java.butterfly.common.dto.CommonConstant;
import com.java.butterfly.system.dto.ResetPwdDTO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.java.butterfly.common.dto.ResultMsg;
import com.java.butterfly.common.util.EmptyUtils;
import com.java.butterfly.common.util.encrypt.Md5Encryption;
import com.java.butterfly.system.ctrl.UserLoginController;
import com.java.butterfly.system.dao.SysUserInfoMapper;
import com.java.butterfly.system.entity.SysUserInfo;
import com.java.butterfly.system.enums.SysUserRoleStatusEnums;
import com.java.butterfly.system.service.IUserLoginService;

@Service
public class UserLoginServiceImpl implements IUserLoginService {
    //    Log4j
    private static Logger logger = Logger.getLogger(UserLoginController.class);
    
    @Resource
    private SysUserInfoMapper sysuserinfoMapper;
    
    @Override
    public ResultMsg loginCheck(String userName, String userPwd) {
        logger.info(" >> 用户登录 >> " + userName);
        if (EmptyUtils.isEmpty(userName) || EmptyUtils.isEmpty(userPwd)) {
            return new ResultMsg(false, "用户名密码不能为空!");
        }
        SysUserInfo record = new SysUserInfo();
        record.setUserName(userName);
        record.setUserPwd(Md5Encryption.MD5Encription(userPwd));
        List<SysUserInfo> userslist = this.sysuserinfoMapper.selectByExample(record);
        if (EmptyUtils.isEmpty(userslist)) {
            logger.info(" >> 用户名或密码不正确 >> " + userName);
            return new ResultMsg(false, "用户名或密码不正确!");
        }
        if (userslist.size() > 1) {
            logger.error(" >> 登录异常，存在多个用户! >> " + userName);
            return new ResultMsg(false, "账号登录异常!");
        }
        System.out.println(userslist.get(0).getStatus());
        if (SysUserRoleStatusEnums.USER_SATUS_LOCK.getCode().equals(userslist.get(0).getStatus())) {
            logger.error(" >> 用户被锁定 >> " + userName);
            return new ResultMsg(false, "用户被锁定!");
        }
        logger.info("  >> 登录成功  >> " + userName);
        return new ResultMsg(true, userslist.get(0));
        
    }
    
    @Override
    public ResultMsg resetPwd(ResetPwdDTO dto) {
        logger.info(">> 重置密码..");
        if (null == dto) {
            logger.info("参数错误 dto is null");
            return new ResultMsg(false, CommonConstant.PARAMETER_ERROR);
        }
        if (EmptyUtils.isEmpty(dto.getUserId())) {
            logger.info("参数错误 userID is null");
            return new ResultMsg(false, CommonConstant.PARAMETER_ERROR);
        }
        if (EmptyUtils.isEmpty(dto.getPwd())) {
            logger.info("参数错误 pwd is null");
            return new ResultMsg(false, CommonConstant.PARAMETER_ERROR);
        }
        if (EmptyUtils.isEmpty(dto.getNewPwd())) {
            logger.info("参数错误 newpwd is null");
            return new ResultMsg(false, CommonConstant.PARAMETER_ERROR);
        }
        if (EmptyUtils.isEmpty(dto.getNewPwdConfirm())) {
            logger.info("参数错误 newPwdConfirm is null");
            return new ResultMsg(false, CommonConstant.PARAMETER_ERROR);
        }
        if (!dto.getNewPwd().equals(dto.getNewPwdConfirm())) {
            logger.info("两次输入密码不一致");
            return new ResultMsg(false, CommonConstant.ERROR + " 两次输入密码不一致");
        }
        
        SysUserInfo userinfo = this.sysuserinfoMapper.selectByPrimaryKey(dto.getUserId());
        if (null == userinfo) {
            logger.info("无法查询到用户信息");
            return new ResultMsg(false, CommonConstant.ERROR + " 无法查询到用户信息");
        }
        if (EmptyUtils.isEmpty(userinfo.getUserPwd())) {
            logger.info("原密码异常");
            return new ResultMsg(false, CommonConstant.ERROR + " 原密码异常");
        }
        if (!userinfo.getUserPwd().equals(Md5Encryption.MD5Encription(dto.getPwd()))) {
            logger.info("原密码输入错误");
            return new ResultMsg(false, CommonConstant.ERROR + " 原密码输入错误");
        }
        userinfo.setUserPwd(Md5Encryption.MD5Encription(dto.getNewPwd()));
        userinfo.setModifyDate(new Date());
        this.sysuserinfoMapper.updateByPrimaryKeySelective(userinfo);
        logger.info("更改密码成功..userid：" + dto.getUserId());
        return new ResultMsg(true, CommonConstant.SUCCESS);
    }
}
