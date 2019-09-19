package com.java.butterfly.business.test.service.impl.rabbitMq;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by lu.xu on 2017/8/22.
 * TODO: MQ接收测试
 */
public class SpringAMQPConsumerTest {
    private static Logger logger = Logger.getLogger(SpringAMQPConsumerTest.class);
    
    private static CachingConnectionFactory cachingConnectionFactory = null;
    static {
        try {
            com.rabbitmq.client.ConnectionFactory rabbitCf = new com.rabbitmq.client.ConnectionFactory();
            rabbitCf.setHost(MqPropertiesUtil.MQ_HOST);
            rabbitCf.setPort(MqPropertiesUtil.MQ_PORT);
            rabbitCf.setUsername(MqPropertiesUtil.MQ_USER);
            rabbitCf.setPassword(MqPropertiesUtil.MQ_PWD);
            rabbitCf.setConnectionTimeout(MqPropertiesUtil.MQ_TIME_OUT);
            cachingConnectionFactory = new CachingConnectionFactory(rabbitCf);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public static void main(String[] args) throws Exception {
        try {
            consumerTest("TEST_DURABLE_QUEUENAME");
            
        } catch (Exception e) {
            throw e;
        } finally {
            /**关闭连接*/
            Thread.sleep(3000);
            /**关闭connection*/
            if (cachingConnectionFactory != null) {
                cachingConnectionFactory.destroy();
                System.out.println("\n");
                logger.info("rabbitmq连接已销毁");
            }
        }
        
    }
    
    /**
     *收消息
     * @throws Exception
     */
    public static void consumerTest(String queueName) throws Exception {
        RabbitAdmin consumerAdmin = new RabbitAdmin(cachingConnectionFactory);
        Integer size = (Integer) consumerAdmin.getQueueProperties(queueName).get("QUEUE_MESSAGE_COUNT");
        logger.info("当前队列大小:" + size);
        if (null == size || 0 == size) {
            logger.info("队列中无消息，程序结束..");
            return;
        }
        RabbitTemplate consumerTemplate = new RabbitTemplate(cachingConnectionFactory);
        for (int i = 1; i <= size; i++) {
            Object obj = consumerTemplate.receiveAndConvert(queueName);
            System.out.println("接收消息：" + String.valueOf(obj));
        }
        logger.info(">>接收完毕...");
        
    }
}
