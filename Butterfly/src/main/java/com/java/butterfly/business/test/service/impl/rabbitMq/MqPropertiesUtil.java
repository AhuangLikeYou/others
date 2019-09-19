package com.java.butterfly.business.test.service.impl.rabbitMq;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.java.butterfly.common.util.EmptyUtils;
import com.java.butterfly.common.util.PropertiesLoadUtil;

/**
 * Created by lu.xu on 2017/8/17.
 * TODO: rabbitmq 配置文件加载
 */
@Service
public class MqPropertiesUtil {
    
    private static Logger logger = Logger.getLogger(MqPropertiesUtil.class);
    
    /**mq配置信息*/
    private static final Properties mqProperties;
    
    /**mq配置文件名称以及各个属性名称*/
    private static final String mqPropertiesName = "mq.properties";
    
    private static final String MQ_HOST_KEY = "mq.host";
    
    private static final String MQ_PORT_KEY = "mq.port";
    
    private static final String MQ_USER_KEY = "mq.user";
    
    private static final String MQ_PWD_KEY = "mq.pwd";
    
    private static final String MQ_TIME_OUT_KEY = "mq.timeOut";
    
    /**属性值*/
    public static final String MQ_HOST;
    
    public static final int MQ_PORT;
    
    public static final String MQ_USER;
    
    public static final String MQ_PWD;
    
    public static final int MQ_TIME_OUT;
    
    static {
        logger.info(">>加载mq配置文件");
        Properties temp = null;
        try {
            temp = PropertiesLoadUtil.load(mqPropertiesName);
        } catch (Exception e) {
            logger.error(">>初始化加载mq配置文件出错...");
            logger.error(e.getMessage());
        } finally {
            mqProperties = temp;
            MQ_HOST = mqProperties.getProperty(MQ_HOST_KEY);
            String mqPort = mqProperties.getProperty(MQ_PORT_KEY);
            if (EmptyUtils.isNotEmpty(mqPort)) {
                MQ_PORT = Integer.valueOf(mqPort);
            } else {
                MQ_PORT = 0;
            }
            MQ_USER = mqProperties.getProperty(MQ_USER_KEY);
            MQ_PWD = mqProperties.getProperty(MQ_PWD_KEY);
            String mqTimeOut = mqProperties.getProperty(MQ_TIME_OUT_KEY);
            if (EmptyUtils.isNotEmpty(mqTimeOut)) {
                MQ_TIME_OUT = Integer.valueOf(mqTimeOut);
            } else {
                MQ_TIME_OUT = 3000;
            }
        }
        logger.info(">>加载mq配置文件over");
    }
    
}
