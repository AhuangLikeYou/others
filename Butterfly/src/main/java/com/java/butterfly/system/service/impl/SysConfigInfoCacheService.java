package com.java.butterfly.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.java.butterfly.common.util.BeanUtils;
import com.java.butterfly.common.util.EmptyUtils;
import com.java.butterfly.system.dao.SysConfigInfoMapper;
import com.java.butterfly.system.dto.ConfigInfoDTO;
import com.java.butterfly.system.entity.SysConfigInfo;
import com.java.butterfly.system.enums.SysConfigEnums;

/**
 * Created by lu.xu on 2017/7/14.
 * TODO:系统配置缓存操作类
 */
public class SysConfigInfoCacheService {
    
    private static final Logger logger = LoggerFactory.getLogger(SysConfigInfoCacheService.class);
    
    @Resource
    private SysConfigInfoMapper sysConfigInfoMapper;
    
    /**
     * 静态变量当做缓存
     * key:type+SUFFIX +configkey
     * value:configValue
     */
    private static HashMap<String, SysConfigInfo> configCache = new HashMap<>();
    
    /**
     * 使用type+configkey+key作为缓存的key
     */
    private static final String SUFFIX = "*%*";
    
    //定义最大缓存条数
    private static final int MAX_CACHE = 10000;
    
    //定义单次查询DB数据
    private static final int SINGLE_LOAD = 100;
    
    public static String getCache(String type, String key) {
        SysConfigInfo config = getCacheObj(type, key);
        if (null == config) {
            return null;
        }
        logger.info("获取缓存：key:" + cacheKeyGenerator(type, key) + " value: " + config.getConfigValue());
        return config.getConfigValue();
    }
    
    public static SysConfigInfo getCacheObj(String type, String key) {
        return configCache.get(cacheKeyGenerator(type, key));
    }
    
    public static List<SysConfigInfo> getCacheObj(String type) {
        String key = cacheKeyGenerator(type);
        if (null == key) {
            return null;
        }
        List<SysConfigInfo> list = new ArrayList<>();
        for (Map.Entry<String, SysConfigInfo> entity : configCache.entrySet()) {
            if (entity.getKey().indexOf(key) > -1) {
                list.add(entity.getValue());
            }
            
        }
        return list;
    }
    
    public static void updateConfigCache(String type, String key, String value) throws Exception {
        updateConfigCache(type, new SysConfigInfo(type, key, value));
    }
    
    public static void updateConfigCache(String type, SysConfigInfo configInfo) throws Exception {
        /**
         * 踩坑之旅
         * hashmap中存放的value是对象的引用
         * 如果编码时候：
         * SysConfigInfo config=new SysConfigInfo();
         * config.setType("1");
         * config.setKey("1");
         * config.setValue("1");
         * SysConfigInfoCacheService.updateConfigCache(config.getType(),config);
         * config.setType("2");
         * config.setKey("2");
         * config.setValue("2");
         * SysConfigInfoCacheService.updateConfigCache(config.getType(),config);
         * 则最终获取type=2+key=2的value时，会读取到第一个对象的信息..
         * 所以此处使用new 对象保证不出现引用重复
         */
        SysConfigInfo config = new SysConfigInfo();
        BeanUtils.copyProperties(configInfo, config);
        
        String cacheKey = cacheKeyGenerator(type, config.getConfigKey());
        if (!configCache.containsKey(cacheKey)) {
            if (configCache.size() >= MAX_CACHE) {
                logger.info("缓存数据超过:" + MAX_CACHE + "条，不再将数据放入缓存...");
                return;
            }
        }
        configCache.put(cacheKey, config);
    }
    
    public static void deleteConfigCache(String type, String key) {
        logger.info(">> 删除系统缓存:type:" + type + "-key:" + key);
        String cacheKey = cacheKeyGenerator(type, key);
        configCache.remove(cacheKey);
    }
    
    private static String cacheKeyGenerator(String type, String key) {
        if (EmptyUtils.isNotEmpty(type, key)) {
            return type + SUFFIX + key;
        }
        return null;
    }
    
    private static String cacheKeyGenerator(String type) {
        if (EmptyUtils.isNotEmpty(type)) {
            return type + SUFFIX;
        }
        return null;
    }
    
    public void refreshAll() throws Exception {
        logger.info(">> 系统初始化加载sysconfig缓存开始..");
        configCache = new HashMap<>();
        ConfigInfoDTO configInfoD = new ConfigInfoDTO();
        configInfoD.setIsCache(SysConfigEnums.IS_CACHE_Y.getCode());
        int count = this.sysConfigInfoMapper.configListByPageCount(configInfoD);
        if (count > SINGLE_LOAD) {
            logger.info(">> 系统初始化加载sysconfig缓存大于" + SINGLE_LOAD + "条，开始分批加载..");
            for (int i = 0; i <= count; i += SINGLE_LOAD) {
                if (i > MAX_CACHE) {
                    logger.info(">>DB需要缓存数量超过规定数量，不再加载缓存...");
                    break;
                }
                configInfoD.setLimit(i);
                configInfoD.setLimit(i + SINGLE_LOAD);
                List<SysConfigInfo> configs = this.sysConfigInfoMapper.configListByPage(configInfoD);
                if (null == configs || configs.size() == 0) {
                    break;
                }
                for (SysConfigInfo info : configs) {
                    this.updateConfigCache(info.getConfigType(), info);
                }
            }
        } else {
            configInfoD.setLimit(0);
            configInfoD.setLimit(SINGLE_LOAD);
            List<SysConfigInfo> configs = this.sysConfigInfoMapper.configListByPage(configInfoD);
            for (SysConfigInfo info : configs) {
                this.updateConfigCache(info.getConfigType(), info);
            }
        }
        logger.info(">> 系统初始化加载sysconfig缓存完毕！共加载缓存：" + configCache.size() + "条");
    }
    
}
