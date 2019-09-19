package com.java.butterfly.business.test.service.impl.rabbitMq;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by lu.xu on 2017/8/22.
 * TODO:DLX，Dead-Letter-Exchange（死信邮箱）
 * 当消息在一个队列中变成死信后，它能被重新publish到另一个Exchange，这个Exchange就是DLX
 * 消息变成死信一向有以下几种情况：
        消息被拒绝（basic.reject or basic.nack）并且requeue=false
        消息TTL过期
        队列达到最大长度
 */
public class SpringAMQPDLXTest {
    private static Logger logger = Logger.getLogger(SpringAMQPDLXTest.class);
    
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
            testDLX();
            
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
    
    public static void testDLX() throws Exception {
        System.out.println("\n\n\n");
        logger.info(">>dlx 队列测试开始..");
        final String exchangeName = "TEST_dlx_EXCHANGE";
        final String queueName = "TEST_DLX_QUEUENAME1";
        final String queueName2 = "TEST_DLX_QUEUENAME2";
        final String routingKey = "TEST_DLX_ROUTINGKEY1";
        final String routingKey2 = "TEST_DLX_ROUTINGKEY2";
        
        DirectExchange exchange = new DirectExchange(exchangeName);
        
        Map<String, Object> arguments = new HashMap<>();
        /**设置过期时间*/
        arguments.put("x-message-ttl", 5000);
        /**设置死信队列*/
        arguments.put("x-dead-letter-exchange", exchangeName);
        arguments.put("x-dead-letter-routing-key", routingKey2);
        Queue queue = new Queue(queueName, false, false, false, arguments);
        Queue queue2 = new Queue(queueName2, false, false, false, null);
        
        RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
        admin.declareExchange(exchange);
        admin.declareQueue(queue);
        admin.declareQueue(queue2);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
        admin.declareBinding(BindingBuilder.bind(queue2).to(exchange).with(routingKey2));
        
        /**发送*/
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        for (int i = 0; i < 5; i++) {
            String msg = "hello ttl " + i;
            System.out.println("发送:" + msg);
            template.convertAndSend(exchangeName, routingKey, msg);
        }
        logger.info(">>发送完毕开始计算大小...");
        Thread.sleep(3000);
        RabbitAdmin consumerAdmin = new RabbitAdmin(cachingConnectionFactory);
        Integer size = (Integer) consumerAdmin.getQueueProperties(queueName).get("QUEUE_MESSAGE_COUNT");
        logger.info(">>过期时间过前，队列大小：" + size);
        Thread.sleep(4000);
        size = (Integer) consumerAdmin.getQueueProperties(queueName).get("QUEUE_MESSAGE_COUNT");
        logger.info(">>过期时间过后，队列大小：" + size);
        Thread.sleep(5000);
        size = (Integer) consumerAdmin.getQueueProperties(queueName2).get("QUEUE_MESSAGE_COUNT");
        logger.info(">>死信队列大小：" + size);
        
        logger.info(">>dlx 队列测试完毕...");
        
    }
    
}
