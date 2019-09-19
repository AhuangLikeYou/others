package com.java.butterfly.system.service;

import java.util.Map;

import com.java.butterfly.common.dto.ResultMsg;
import com.java.butterfly.system.dto.ConfigInfoDTO;
import com.java.butterfly.system.entity.SysConfigInfo;

/**
 * Created by lu.xu on 2017/7/14.
 * TODO:
 */
public interface ISysConfigInfoService {
    
    public Map getConfigList(ConfigInfoDTO configInfoDTO);
    
    public ResultMsg addConfig(SysConfigInfo sysConfigInfo) throws Exception;
    
    public ResultMsg deleteConfig(String ids);
    
    public ResultMsg updateConfig(SysConfigInfo sysConfigInfo) throws Exception;
    
    public SysConfigInfo getById(String id);
}
