package com.java.butterfly.system.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.java.butterfly.common.dto.ResultMsg;
import com.java.butterfly.common.util.EmptyUtils;
import com.java.butterfly.common.util.IdGeneratorUtils;
import com.java.butterfly.common.util.TabelData;
import com.java.butterfly.system.ctrl.UserInfoController;
import com.java.butterfly.system.dao.SysRoleInfoMapper;
import com.java.butterfly.system.dto.RoleInfoDTO;
import com.java.butterfly.system.entity.SysRoleInfo;
import com.java.butterfly.system.enums.SysUserRoleStatusEnums;
import com.java.butterfly.system.service.IRoleInfoService;

@Service
public class RoleInfoServiceImpl implements IRoleInfoService {
    
    private static Logger logger = Logger.getLogger(UserInfoController.class);
    
    @Resource
    private SysRoleInfoMapper sysRoleInfoMapper;
    
    @Override
    public Map getRoleListByPage(RoleInfoDTO roleInfoDTO) {
        String keyWords = roleInfoDTO.getKeyWords();
        logger.info(">>角色查询，keyWords:" + keyWords);
        if (EmptyUtils.isNotEmpty(keyWords)) {
            roleInfoDTO.setKeyWords("%" + keyWords + "%");
        } else {
            roleInfoDTO.setKeyWords(null);
        }
        List<SysRoleInfo> list = this.sysRoleInfoMapper.getRoleListByPage(roleInfoDTO);
        int count = this.sysRoleInfoMapper.getRoleListByPageCount(roleInfoDTO.getKeyWords());
        return TabelData.tabelDataResult(count, list);
    }
    
    @Override
    public ResultMsg deleteByIds(String ids) {
        if (EmptyUtils.isEmpty(ids)) {
            return new ResultMsg(false, "参数错误");
        }
        List list = Arrays.asList(ids.split(","));
        int count = this.sysRoleInfoMapper.deleteByPrimaryKeys(list);
        return new ResultMsg(true, "已删除" + count + "条记录");
        
    }
    
    @Override
    public ResultMsg updateDetails(SysRoleInfo roleInfo) {
        if (null == roleInfo || EmptyUtils.isEmpty(roleInfo.getRoleId())
            || EmptyUtils.isEmpty(roleInfo.getRoleName())) {
            return new ResultMsg(false, "参数错误");
        }
        roleInfo.setModifyDate(new Date());
        this.sysRoleInfoMapper.updateByPrimaryKeySelective(roleInfo);
        return new ResultMsg(true, "修改成功");
    }
    
    @Override
    public ResultMsg addRole(SysRoleInfo roleInfo) {
        if (null == roleInfo || EmptyUtils.isEmpty(roleInfo.getRoleName())) {
            return new ResultMsg(false, "参数错误");
        }
        roleInfo.setRoleId(IdGeneratorUtils.getSerialNo());
        roleInfo.setCreateDate(new Date());
        roleInfo.setModifyDate(new Date());
        if (EmptyUtils.isEmpty(roleInfo.getStatus())) {
            roleInfo.setStatus(SysUserRoleStatusEnums.ROLE_SATUS_LOCK.getCode());
        }
        this.sysRoleInfoMapper.insertSelective(roleInfo);
        return new ResultMsg(true, "添加成功!");
    }
    
    @Override
    public ResultMsg updateStatus(String id) {
        if (EmptyUtils.isEmpty(id)) {
            return new ResultMsg(false, "参数错误");
        }
        SysRoleInfo roleInfo = this.sysRoleInfoMapper.selectByPrimaryKey(id);
        SysRoleInfo info = new SysRoleInfo();
        info.setRoleId(id);
        info.setModifyDate(new Date());
        if (SysUserRoleStatusEnums.ROLE_SATUS_NORMAL.getCode().equals(roleInfo.getStatus())) {
            info.setStatus(SysUserRoleStatusEnums.ROLE_SATUS_LOCK.getCode());
        } else if (SysUserRoleStatusEnums.ROLE_SATUS_LOCK.getCode().equals(roleInfo.getStatus())) {
            info.setStatus(SysUserRoleStatusEnums.ROLE_SATUS_NORMAL.getCode());
        } else {
            return new ResultMsg(false, "原状态异常");
        }
        this.sysRoleInfoMapper.updateByPrimaryKeySelective(info);
        return new ResultMsg(true, "状态更新成功");
    }
    
    @Override
    public SysRoleInfo getById(String id) {
        return this.sysRoleInfoMapper.selectByPrimaryKey(id);
    }
}
