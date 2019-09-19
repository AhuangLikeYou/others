package com.java.butterfly.business.test.service.impl.rabbitMq;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by lu.xu on 2017/8/22.
 * TODO: spring发送rabbit
 * 多次声明同一个交换机、队列，以及同一个交换机用同一个routingkey绑定不同的队列测试
 */
public class SpringAMQPRoutingKeyTest {
    private static Logger logger = Logger.getLogger(SpringAMQPRoutingKeyTest.class);
    
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
            routingKeyTest();
            
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
     * 路由键相同测试
     * @throws Exception
     */
    public static void routingKeyTest() throws Exception {
        System.out.println("\n\n\n");
        logger.info(">>测试routingKey开始..");
        final String exchangeName = "TEST_EXCHANGE";
        final String queueName1 = "TEST_QUEUENAME1";
        final String queueName2 = "TEST_QUEUENAME2";
        final String routingKey = "TESTT_ROUTINGKEY";
        /**声明交换机和队列*/
        DirectExchange exchange = new DirectExchange(exchangeName);
        Queue queue = new Queue(queueName1);
        Queue queue2 = new Queue(queueName2);
        
        RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
        admin.declareExchange(exchange);
        admin.declareQueue(queue);
        admin.declareQueue(queue2);
        
        /**进行绑定*/
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
        admin.declareBinding(BindingBuilder.bind(queue2).to(exchange).with(routingKey));
        
        /**对exchange 和routingkey 发送消息*/
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        for (int i = 0; i < 5; i++) {
            String msg = "hello  " + i;
            System.out.println("发送:" + msg);
            template.convertAndSend(exchangeName, routingKey, msg);
        }
        
        /**避免延迟情况下，统计队列大小出错*/
        Thread.sleep(3000);
        RabbitAdmin consumerAdmin = new RabbitAdmin(cachingConnectionFactory);
        Integer size = (Integer) consumerAdmin.getQueueProperties(queueName1).get("QUEUE_MESSAGE_COUNT");
        Integer size2 = (Integer) consumerAdmin.getQueueProperties(queueName2).get("QUEUE_MESSAGE_COUNT");
        
        logger.info(queueName1 + "队列大小:" + size);
        logger.info(queueName2 + "队列大小:" + size2);
        
    }
    
}
