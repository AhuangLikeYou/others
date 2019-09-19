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
 * TODO: rabbitmq持久化测试
 */
public class SpringAMQPDurableTest {
    private static Logger logger = Logger.getLogger(SpringAMQPDurableTest.class);
    
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
            //            unDurableTest();
            durableTest();
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
     *非持久化测试
     * @throws Exception
     */
    public static void unDurableTest() throws Exception {
        System.out.println("\n\n\n");
        logger.info(">>测试uddurableTest开始..");
        final String exchangeName = "TEST_DURABLE_EXCHANGE";
        final String queueName1 = "TEST_DURABLE_QUEUENAME";
        final String routingKey = "TEST_DURABLE_ROUTINGKEY";
        /**
         * 声明交换机和队列
         * 交换机和队列默认是持久化的，声明的时候选择非默认
         */
        DirectExchange exchange = new DirectExchange(exchangeName, false, false);
        Queue queue = new Queue(queueName1, false, false, false);
        RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
        admin.declareExchange(exchange);
        admin.declareQueue(queue);
        /**进行绑定*/
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
        /**对exchange 和routingkey 发送消息*/
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        for (int i = 0; i < 5; i++) {
            String msg = "hello  " + i;
            System.out.println("发送:" + msg);
            template.convertAndSend(exchangeName, routingKey, msg);
        }
    }
    
    /**
     *持久化测试
     * @throws Exception
     */
    public static void durableTest() throws Exception {
        System.out.println("\n\n\n");
        logger.info(">>测试durableTest开始..");
        final String exchangeName = "TEST_DURABLE_EXCHANGE";
        final String queueName1 = "TEST_DURABLE_QUEUENAME";
        final String routingKey = "TEST_DURABLE_ROUTINGKEY";
        /**
         * 声明交换机和队列
         * 交换机和队列默认是持久化的，声明的时候选择非默认
         */
        DirectExchange exchange = new DirectExchange(exchangeName, false, false);
        Queue queue = new Queue(queueName1);
        RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
        admin.declareExchange(exchange);
        admin.declareQueue(queue);
        /**进行绑定*/
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
        /**对exchange 和routingkey 发送消息*/
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        for (int i = 0; i < 5; i++) {
            String msg = "hello  " + i;
            System.out.println("发送:" + msg);
            template.convertAndSend(exchangeName, routingKey, msg);
        }
    }
}
