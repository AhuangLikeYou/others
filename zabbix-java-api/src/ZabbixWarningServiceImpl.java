package com.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import io.github.hengyunabc.zabbix.api.Request;
import io.github.hengyunabc.zabbix.api.RequestBuilder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by lu.xu on 2017/7/5.
 * TODO:查询zabbix中异常事件
 * 在zabbix中，异常事件是在触发器中，所以查询触发器信息即可
 */
@Service
public class ZabbixWarningServiceImpl implements IZabbixWarningService {
    private static Logger logger = Logger.getLogger(ZabbixWarningServiceImpl.class);
      
   
    
    /**
     * 获取所有zabbix告警详情
     * 模糊查询见：ZabbixHostServiceImpl.java
     * @return
     * @throws Exception
     */
    private Object getErrorTriggerDetail() throws Exception {
        logger.info(">> 获取所有zabbix有告警且未确认触发器详情.."); 
        
        //过滤结果集
        JSONObject filter = new JSONObject();
        //value:0 触发器状态处于正常；1问题
        filter.put("value", 1);
        
        RequestBuilder builder = RequestBuilder.newBuilder()
            //调用接口，详见zabbix 官方api接口说明
            .method("trigger.get")
            //见ZabbixHostServiceImpl.java对output的说明
            .paramEntry("output", "extend")
            //            .paramEntry("output", new String[] {"description", "value", "status", "priority"})
            //已启用的触发器
            .paramEntry("active", true)
            //子查询
            .paramEntry("selectHosts", new String[] {"host", "name"})
            //过滤结果集
            .paramEntry("filter", filter)
            //未确认的事件
            .paramEntry("withUnacknowledgedEvents", true)
            //处于问题状态的事件
            .paramEntry("only_true", true)
            //排序
            .paramEntry("sortfield", "lastchange")
            .paramEntry("sortorder", "DESC")
            //加上则说明求结果集的和
//            .paramEntry("countOutput", true)
            //取20条
            .paramEntry("limit", 20);
            return ZabbixUtil.call(builder.build());
         
    }
     
}
