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
import com.java.butterfly.system.dao.SysConfigInfoMapper;
import com.java.butterfly.system.dto.ConfigInfoDTO;
import com.java.butterfly.system.entity.SysConfigInfo;
import com.java.butterfly.system.enums.SysConfigEnums;
import com.java.butterfly.system.service.ISysConfigInfoService;

/**
 * Created by lu.xu on 2017/7/14.
 * TODO:系统配置业务实现类
 */
@Service
public class SysConfigInfoServiceImpl implements ISysConfigInfoService {
    private static Logger logger = Logger.getLogger(SysConfigInfoServiceImpl.class);
    
    @Resource
    private SysConfigInfoMapper sysConfigInfoMapper;
    
    @Override
    public Map getConfigList(ConfigInfoDTO configInfoDTO) {
        if (null == configInfoDTO) {
            configInfoDTO = new ConfigInfoDTO();
        }
        if (EmptyUtils.isEmpty(configInfoDTO.getKeyWords())) {
            configInfoDTO.setKeyWords(null);
        } else {
            configInfoDTO.setKeyWords("%" + configInfoDTO.getKeyWords().trim() + "%");
        }
        if (EmptyUtils.isEmpty(configInfoDTO.getKey())) {
            configInfoDTO.setKey(null);
        } else {
            configInfoDTO.setKey("%" + configInfoDTO.getKey().trim() + "%");
        }
        if (EmptyUtils.isEmpty(configInfoDTO.getType())) {
            configInfoDTO.setType(null);
        } else {
            configInfoDTO.setType("%" + configInfoDTO.getType().trim() + "%");
        }
        
        List<SysConfigInfo> data = this.sysConfigInfoMapper.configListByPage(configInfoDTO);
        int count = this.sysConfigInfoMapper.configListByPageCount(configInfoDTO);
        return TabelData.tabelDataResult(count, data);
    }
    
    @Override
    public ResultMsg addConfig(SysConfigInfo sysConfigInfo) throws Exception {
        if (null == sysConfigInfo
            || !EmptyUtils.isNotEmpty(sysConfigInfo.getConfigType(), sysConfigInfo.getConfigKey())) {
            return new ResultMsg(false, CommonConstant.PARAMETER_ERROR);
        }
        logger.info(">> 开始添加配置");
        List<SysConfigInfo> list = this.sysConfigInfoMapper
            .configListByTypeAndKey(sysConfigInfo.getConfigType().trim(), sysConfigInfo.getConfigKey().trim());
        if (null != list && list.size() > 0) {
            return new ResultMsg(false, "存在此配置");
        }
        sysConfigInfo.setConfigId(IdGeneratorUtils.getSerialNo());
        sysConfigInfo.setConfigType(sysConfigInfo.getConfigType().trim());
        sysConfigInfo.setConfigKey(sysConfigInfo.getConfigKey().trim());
        sysConfigInfo.setCreateDate(new Date());
        sysConfigInfo.setModifyDate(new Date());
        this.sysConfigInfoMapper.insertSelective(sysConfigInfo);
        if (sysConfigInfo.getIscache() != null
            && SysConfigEnums.IS_CACHE_Y.getCode().equals(sysConfigInfo.getIscache())) {
            logger.info(">>添加成功，开始更新缓存");
            SysConfigInfoCacheService.updateConfigCache(sysConfigInfo.getConfigType(), sysConfigInfo
                .getConfigKey(), sysConfigInfo.getConfigValue());
        }
        return new ResultMsg(true, CommonConstant.SUCCESS);
    }
    
    @Override
    public ResultMsg deleteConfig(String ids) {
        if (EmptyUtils.isEmpty(ids)) {
            return new ResultMsg(false, CommonConstant.PARAMETER_ERROR);
        }
        List<SysConfigInfo> configs = this.sysConfigInfoMapper.selectByPrimaryKeys(Arrays.asList(ids.split(",")));
        if (EmptyUtils.isEmpty(configs)) {
            return new ResultMsg(false, CommonConstant.ERROR + ",未查询到记录");
        }
        int count = this.sysConfigInfoMapper.deleteByPrimaryKeys(Arrays.asList(ids.split(",")));
        for (SysConfigInfo config : configs) {
            if (SysConfigEnums.IS_CACHE_Y.getCode().equals(config.getIscache())) {
                logger.info(">>删除成功，开始删除缓存");
                SysConfigInfoCacheService.deleteConfigCache(config.getConfigType(), config.getConfigKey());
            }
        }
        return new ResultMsg(true, CommonConstant.SUCCESS + "，共删除" + count + "条记录");
    }
    
    /**
     * 更新系统配置
     * @param sysConfigInfo
     * @return
     */
    @Override
    public ResultMsg updateConfig(SysConfigInfo sysConfigInfo) throws Exception {
        if (null == sysConfigInfo || !EmptyUtils
            .isNotEmpty(sysConfigInfo.getConfigType(), sysConfigInfo.getConfigKey(), sysConfigInfo.getConfigId())) {
            return new ResultMsg(false, CommonConstant.PARAMETER_ERROR);
        }
        sysConfigInfo.setConfigType(sysConfigInfo.getConfigType().trim());
        sysConfigInfo.setConfigKey(sysConfigInfo.getConfigKey().trim());
        logger.info(">>更新系统配置,开始删除");
        List<SysConfigInfo> configs = this.sysConfigInfoMapper
            .configListByTypeAndKey(sysConfigInfo.getConfigType(), sysConfigInfo.getConfigKey());
        if (null != configs && configs.size() > 1) {
            logger.error(CommonConstant.ERROR + "，找到多项系统配置，请联系管理员");
            return new ResultMsg(false, CommonConstant.ERROR + "，找到多项系统配置，请联系管理员");
        }
        //判断是否重复
        if (null != configs && configs.size() == 1) {
            String currentConfigId = configs.get(0).getConfigId();
            //更改的不是是同一条
            if (!currentConfigId.equals(sysConfigInfo.getConfigId())) {
                logger.error(CommonConstant.ERROR + "，存在相同的配置项");
                return new ResultMsg(false, CommonConstant.ERROR + "，存在相同的配置项");
            }
        }
        //吧老缓存删除，且不判断是否存在缓存
        SysConfigInfo oldConfig = this.sysConfigInfoMapper.selectByPrimaryKey(sysConfigInfo.getConfigId());
        SysConfigInfoCacheService.deleteConfigCache(oldConfig.getConfigType(), oldConfig.getConfigKey());
        
        sysConfigInfo.setModifyDate(new Date());
        this.sysConfigInfoMapper.updateByPrimaryKeySelective(sysConfigInfo);
        logger.info(">>更新系统配置成功");
        if (SysConfigEnums.IS_CACHE_Y.getCode().equals(sysConfigInfo.getIscache())) {
            logger.info(">>系统配置更新，更新缓存");
            SysConfigInfoCacheService.updateConfigCache(sysConfigInfo.getConfigType(), sysConfigInfo
                .getConfigKey(), sysConfigInfo.getConfigValue());
        }
        return new ResultMsg(true, CommonConstant.SUCCESS);
    }
    
    @Override
    public SysConfigInfo getById(String id) {
        return this.sysConfigInfoMapper.selectByPrimaryKey(id);
    }
    
}
