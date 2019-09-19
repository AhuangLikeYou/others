package com.test;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import io.github.hengyunabc.zabbix.api.Request;
import io.github.hengyunabc.zabbix.api.RequestBuilder;

/**
 * Created by lu.xu on 2017/7/5.
 * TODO: 获取zabbix的host信息
 * 实现模糊查询
 */
@Service
public class ZabbixHostServiceImpl implements IZabbixHostService {
    private static Logger logger = Logger.getLogger(ZabbixHostServiceImpl.class);
    
    /**
     *根据ip开头实现模糊查询host信息
     *可以支持多个ip开头的or查询
     *通配符是“*”相当于sql中的“%”
     */
    @Override
    public Object getHostCount() throws Exception {

        //需要查询的ip，如果需要多个ip查询取并集，使用数组
        String ip="10.82*";
        String[] ips=new String []{"10.82*","10.83*"};
        
        //查询条件
        JSONObject search = new JSONObject();
        search.put("ip", ips);
        logger.info(Arrays.asList(ips));
        
        //开始查询
        Request getRequest = RequestBuilder.newBuilder()
            //调用接口，详见zabbix 官方api
            .method("host.get")
            //声明查询哪些字段，标准输出是extend（默认）
            .paramEntry("output", new String[] {"host", "name"})//.paramEntry("output", "extend")
            //查询host对应的ip，zabbix中以接口的概念表达，接口即ip+端口号，这里相当于是host的子查询，后面的数组声明的是需要子查询的字段，标准输出和output一样是extend
            .paramEntry("selectInterfaces", new String[] {"ip", "port", "hostid"})
            //至关重要，声明为true才能使用模糊查询,通配符是*，相当于sql中的%，如果不声明，则以非模糊匹配方式查询！！！
            .paramEntry("searchWildcardsEnabled", true)
            //searchByAny -如果多个条件并列查询取并集，需要设置为true，相当于sql里面的or查询
            .paramEntry("searchByAny", true)
            /**
             * searchWildcardsEnabled、searchByAny 两个参数的官方说明文档，当时没注意看google了大半天！
             * https://www.zabbix.com/documentation/3.0/manual/api/reference_commentary#common_get_method_parameters
             */
            //查询条件
            .paramEntry("search", search)
            //返回的是结果集的总数，如果需要返回明细，则去掉即可
            .paramEntry("countOutput", true)
            .build();
        return ZabbixUtil.call(getRequest);
    }
    
}
