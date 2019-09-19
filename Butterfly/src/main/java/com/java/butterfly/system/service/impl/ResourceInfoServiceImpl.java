package com.java.butterfly.system.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.java.butterfly.common.dto.CommonConstant;
import com.java.butterfly.common.dto.ResultMsg;
import com.java.butterfly.common.util.EmptyUtils;
import com.java.butterfly.common.util.IdGeneratorUtils;
import com.java.butterfly.common.util.TabelData;
import com.java.butterfly.system.dao.SysResourceInfoMapper;
import com.java.butterfly.system.dto.ResourceDTO;
import com.java.butterfly.system.entity.SysResourceInfo;
import com.java.butterfly.system.enums.SysUserRoleStatusEnums;
import com.java.butterfly.system.service.IResourceInfoService;

@Service
public class ResourceInfoServiceImpl implements IResourceInfoService {
    
    private static Logger logger = Logger.getLogger(ResourceInfoServiceImpl.class);
    
    @Resource
    private SysResourceInfoMapper sysResourceInfoMapper;
    
    @Override
    public Map getResourceByPage(ResourceDTO resourceDTO) {
        String keyWords = resourceDTO.getKeyWords();
        logger.info(">>资源查询，keyWords:" + keyWords);
        if (EmptyUtils.isNotEmpty(keyWords)) {
            resourceDTO.setKeyWords("%" + keyWords + "%");
        } else {
            resourceDTO.setKeyWords(null);
        }
        List<SysResourceInfo> list = this.sysResourceInfoMapper.getListByPage(resourceDTO);
        int count = this.sysResourceInfoMapper.getListByPageCount(resourceDTO.getKeyWords());
        return TabelData.tabelDataResult(count, list);
    }
    
    @Override
    public ResultMsg addResource(SysResourceInfo resourceInfo) {
        if (!this.validateEffectiveness(resourceInfo, true)) {
            return new ResultMsg(false, CommonConstant.PARAMETER_ERROR);
        }
        if (!this.validateDuplicate(resourceInfo, true)) {
            return new ResultMsg(false, CommonConstant.DATA_DUPLICATION);
        }
        resourceInfo.setResourceId(IdGeneratorUtils.getSerialNo());
        resourceInfo.setCreateDate(new Date());
        resourceInfo.setModifyDate(new Date());
        if (EmptyUtils.isEmpty(resourceInfo.getStatus())) {
            resourceInfo.setStatus(SysUserRoleStatusEnums.RESOURCE_SATUS_LOCK.getCode());
        }
        this.sysResourceInfoMapper.insertSelective(resourceInfo);
        return new ResultMsg(true, CommonConstant.SUCCESS);
    }
    
    @Override
    public ResultMsg deleteByIds(String ids) {
        if (EmptyUtils.isEmpty(ids)) {
            return new ResultMsg(false, "参数错误");
        }
        List list = Arrays.asList(ids.split(","));
        int count = this.sysResourceInfoMapper.deleteByPrimaryKeys(list);
        return new ResultMsg(true, "已删除" + count + "条记录");
        
    }
    
    @Override
    public ResultMsg updateStatus(String id) {
        if (EmptyUtils.isEmpty(id)) {
            return new ResultMsg(false, CommonConstant.PARAMETER_ERROR);
        }
        SysResourceInfo resourceInfo = this.sysResourceInfoMapper.selectByPrimaryKey(id);
        SysResourceInfo info = new SysResourceInfo();
        info.setResourceId(id);
        info.setModifyDate(new Date());
        if (SysUserRoleStatusEnums.RESOURCE_SATUS_LOCK.getCode().equals(resourceInfo.getStatus())) {
            info.setStatus(SysUserRoleStatusEnums.RESOURCE_SATUS_NORMAL.getCode());
        } else if (SysUserRoleStatusEnums.RESOURCE_SATUS_NORMAL.getCode().equals(resourceInfo.getStatus())) {
            info.setStatus(SysUserRoleStatusEnums.RESOURCE_SATUS_LOCK.getCode());
        } else {
            return new ResultMsg(false, "原状态异常");
        }
        this.sysResourceInfoMapper.updateByPrimaryKeySelective(info);
        return new ResultMsg(true, CommonConstant.SUCCESS);
    }
    
    @Override
    public SysResourceInfo getById(String id) {
        return this.sysResourceInfoMapper.selectByPrimaryKey(id);
    }
    
    @Override
    public ResultMsg updateDetails(SysResourceInfo resourceInfo) {
        if (!validateEffectiveness(resourceInfo, false)) {
            return new ResultMsg(false, CommonConstant.PARAMETER_ERROR);
        }
        if (!this.validateDuplicate(resourceInfo, false)) {
            return new ResultMsg(false, CommonConstant.DATA_DUPLICATION);
        }
        resourceInfo.setModifyDate(new Date());
        this.sysResourceInfoMapper.updateByPrimaryKeySelective(resourceInfo);
        return new ResultMsg(true, CommonConstant.SUCCESS);
    }
    
    /**
     * 校验参数有效性
     * @param resourceInfo
     * @param isInsert 是否新增
     * @return true 验证通过，false 不通过
     */
    private boolean validateEffectiveness(SysResourceInfo resourceInfo, boolean isInsert) {
        if (null == resourceInfo) {
            return false;
        }
        //不是新增，但是没有主键
        if (!isInsert && EmptyUtils.isEmpty(resourceInfo.getResourceId())) {
            return false;
        }
        return EmptyUtils
            .isNotEmpty(resourceInfo.getResourceCode(), resourceInfo.getResourceName(), resourceInfo.getResourcePath());
    }
    
    /**
     * 校验是否有重复数据
     * code、path 不允许重复定义
     * @param resourceInfo
     * @param isInsert
     * @return true 验证通过，false 验证不通过（有重复，或者不规范）
     */
    private boolean validateDuplicate(SysResourceInfo resourceInfo, boolean isInsert) {
        if (!this.validateEffectiveness(resourceInfo, isInsert)) {
            return false;
        }
        //新增
        if (isInsert) {
            SysResourceInfo resourceInfo1 = new SysResourceInfo();
            resourceInfo1.setResourceCode(resourceInfo.getResourceCode());
            List<SysResourceInfo> list = this.sysResourceInfoMapper.selectByExample(resourceInfo1);
            return EmptyUtils.isEmpty(list) ? true : false;
        } else {
            SysResourceInfo resourceInfo1 = this.sysResourceInfoMapper.selectByPrimaryKey(resourceInfo.getResourceId());
            //没有改变
            if (resourceInfo.getResourceCode().equals(resourceInfo1.getResourceCode())) {
                return true;
            } else {
                return validateDuplicate(resourceInfo, true);
            }
        }
    }
    
}
