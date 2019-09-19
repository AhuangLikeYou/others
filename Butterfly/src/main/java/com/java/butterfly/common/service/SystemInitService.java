package com.java.butterfly.common.service;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ReflectionUtils;

import com.java.butterfly.common.dto.SystemInitBeansDTO;
import com.java.butterfly.common.util.EmptyUtils;

/**
 * Created by lu.xu on 2017/6/19.
 * TODO:系统初始化完毕调用
 */
public class SystemInitService implements ApplicationListener<ContextRefreshedEvent> {
    
    private static Logger logger = Logger.getLogger(SystemInitService.class);
    
    private List<SystemInitBeansDTO> startBeans = null;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        
        /**
         * 系统会存在两个容器，一个是root application context ,
         * 另一个就是我们自己的 projectName-servlet context
         * （后者作为root application context的子容器）
         * 所以root application context 没有parent，此处做判断         * 
         */
        if (event.getApplicationContext().getParent() != null) {
            return;
        }
        logger.info("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        logger.info("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        logger.info(">> 系统启动，执行SystemInit 开始..");
        if (EmptyUtils.isEmpty(startBeans)) {
            logger.info(">> w未找到需要初始化执行程序 ");
            logger.info(">> 系统启动，执行SystemInit完成");
            return;
        }
        
        for (SystemInitBeansDTO startupBean : startBeans) {
            try {
                String beanName = startupBean.getBeanName();
                String initMethod = startupBean.getInitMethod();
                if (EmptyUtils.isEmpty(beanName)) {
                    logger.info("执行SystemInit，检测到beanName为空..");
                    return;
                }
                if (EmptyUtils.isEmpty(initMethod)) {
                    logger.info("执行SystemInit，检测到initMethod为空..");
                    return;
                }
                //spring反射
                ApplicationContext wac = event.getApplicationContext();
                Method mh = ReflectionUtils.findMethod(wac.getBean(beanName).getClass(), initMethod);
                ReflectionUtils.invokeMethod(mh, wac.getBean(beanName));
                
                /**
                 *  java反射，由于独立于spring容器之外，导致被调用方法无法注入属性
                 */
                //                Object targetClass = Class.forName(beanName).newInstance();
                //                Method targetMethod = targetClass.getClass().getMethod(initMethod);
                //                targetMethod.invoke(targetClass);
                
            } catch (NoSuchBeanDefinitionException ex) {
                logger.info("执行SystemInit,初始化未找到方法 beanName：" + startupBean.getBeanName());
            } catch (Exception e) {
                logger.info("执行SystemInit,初始化调用方法失败...beanName：" + startupBean.getBeanName());
                e.printStackTrace();
            }
        }
        
        logger.info(">> 系统启动，执行SystemInit完成");
        logger.info("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        logger.info("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
    }
    
    public List<SystemInitBeansDTO> getStartBeans() {
        return startBeans;
    }
    
    public void setStartBeans(List<SystemInitBeansDTO> startBeans) {
        this.startBeans = startBeans;
    }
}
