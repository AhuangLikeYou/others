package com.java.butterfly.system.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.java.butterfly.common.dto.CommonConstant;
import com.java.butterfly.common.dto.ResultMsg;
import com.java.butterfly.common.util.EmptyUtils;
import com.java.butterfly.common.util.IdGeneratorUtils;
import com.java.butterfly.common.util.TabelData;
import com.java.butterfly.common.util.encrypt.Md5Encryption;
import com.java.butterfly.system.dao.SysUserInfoMapper;
import com.java.butterfly.system.dto.UserInfoDTO;
import com.java.butterfly.system.entity.SysUserInfo;
import com.java.butterfly.system.enums.SysUserRoleStatusEnums;
import com.java.butterfly.system.service.IUserInfoService;

@Service
public class UserInfoServiceImpl implements IUserInfoService {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    
    @Resource
    public SysUserInfoMapper sysuserinfoMapper;
    
    public Map queryListByPage(UserInfoDTO userinfoDTO) {
        if (null == userinfoDTO) {
            userinfoDTO = new UserInfoDTO();
        }
        if (EmptyUtils.isNotEmpty(userinfoDTO.getKeyWords())) {
            userinfoDTO.setKeyWords("%" + userinfoDTO.getKeyWords() + "%");
        } else {
            userinfoDTO.setKeyWords(null);
        }
        logger.info(">> 用户查询 keywords:" + userinfoDTO.getKeyWords());
        return TabelData.tabelDataResult(sysuserinfoMapper
            .queryListByPageCount(userinfoDTO.getKeyWords()), sysuserinfoMapper.queryListByPage(userinfoDTO));
    }
    
    public ResultMsg addUser(SysUserInfo user) throws Exception {
        logger.info(">> 新增用户..");
        if (null == user || EmptyUtils.isEmpty(user.getUserName())) {
            return new ResultMsg(false, "参数错误");
        }
        if (!validateIsUnique(null, user.getUserName())) {
            logger.info("新增用户，存在此用户名");
            return new ResultMsg(false, "存在此用户名!");
        }
        String pwd = EmptyUtils.isEmpty(user.getUserPwd()) ? CommonConstant.DEFAULT_PASSWORD : user.getUserPwd();
        user.setUserPwd(Md5Encryption.MD5Encription(pwd));
        user.setUserId(IdGeneratorUtils.getSerialNo());
        user.setCreateDate(new Date());
        user.setModifyDate(new Date());
        int total = sysuserinfoMapper.insertSelective(user);
        if (total == 1) {
            return new ResultMsg(true, "添加成功");
        }
        return new ResultMsg(false, "添加失败");
    }
    
    public ResultMsg deleteUsers(String usersId) {
        if (EmptyUtils.isEmpty(usersId)) {
            return new ResultMsg(false, "参数错误");
        }
        List list = Arrays.asList(usersId.split(","));
        int count = this.sysuserinfoMapper.deleteByPrimaryKeys(list);
        return new ResultMsg(true, "已删除" + count + "条记录");
    }
    
    public ResultMsg updateUser(SysUserInfo user) throws Exception {
        if (null == user || EmptyUtils.isEmpty(user.getUserId())) {
            return new ResultMsg(true, "参数错误");
        }
        user.setModifyDate(new Date());
        int total = sysuserinfoMapper.updateByPrimaryKeySelective(user);
        if (total == 1) {
            return new ResultMsg(true, "修改成功");
        }
        return new ResultMsg(false, "修改失败");
    }
    
    public ResultMsg updateStatus(String userId) throws Exception {
        if (EmptyUtils.isEmpty(userId)) {
            return new ResultMsg(true, "参数错误");
        }
        SysUserInfo user = this.sysuserinfoMapper.selectByPrimaryKey(userId);
        if (SysUserRoleStatusEnums.USER_SATUS_NORMAL.getCode().equals(user.getStatus())) {
            user.setStatus(SysUserRoleStatusEnums.USER_SATUS_LOCK.getCode());
        } else if (SysUserRoleStatusEnums.USER_SATUS_LOCK.getCode().equals(user.getStatus())) {
            user.setStatus(SysUserRoleStatusEnums.USER_SATUS_NORMAL.getCode());
        } else {
            return new ResultMsg(true, "用户状态错误");
        }
        user.setModifyDate(new Date());
        int total = sysuserinfoMapper.updateByPrimaryKeySelective(user);
        if (total == 1) {
            return new ResultMsg(true, "修改成功");
        }
        return new ResultMsg(false, "修改失败");
    }
    
    /**
     * WebRealm用到了..
     * @param userName
     * @return
     */
    public SysUserInfo queryByUserName(String userName) {
        if (EmptyUtils.isEmpty(userName)) {
            return null;
        }
        return this.sysuserinfoMapper.queryByUserName(userName);
    }
    
    @Override
    public SysUserInfo getByUserId(String userId) {
        return this.sysuserinfoMapper.selectByPrimaryKey(userId);
    }
    
    @Override
    public ResultMsg resetPwd(String userId) {
        if (EmptyUtils.isEmpty(userId)) {
            return new ResultMsg(true, "参数错误");
        }
        SysUserInfo userInfo = new SysUserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserPwd(Md5Encryption.MD5Encription(CommonConstant.DEFAULT_PASSWORD));
        this.sysuserinfoMapper.updateByPrimaryKeySelective(userInfo);
        return new ResultMsg(true, "修改成功");
    }
    
    /**
     * 验证是否唯一
     * @param userId
     * @param userName
     * @return true代表验证成功，false代表验证失败
     */
    public boolean validateIsUnique(String userId, String userName) {
        if (EmptyUtils.isEmpty(userName)) {
            return false;
        }
        //新增
        if (EmptyUtils.isEmpty(userId)) {
            SysUserInfo userInfo = new SysUserInfo();
            userInfo.setUserName(userName);
            List<SysUserInfo> users = this.sysuserinfoMapper.selectByExample(userInfo);
            return EmptyUtils.isEmpty(users) ? true : false;
        } else {
            SysUserInfo userInfo = this.sysuserinfoMapper.selectByPrimaryKey(userId);
            //没有改变
            if (userInfo.getUserName().equals(userName)) {
                return true;
            } else {
                return validateIsUnique(null, userName);
            }
        }
    }
    
}
