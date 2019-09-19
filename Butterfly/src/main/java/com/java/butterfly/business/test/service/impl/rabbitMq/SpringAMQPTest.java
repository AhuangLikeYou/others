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
 * TODO: spring发送rabbit示例
 */
public class SpringAMQPTest {
    private static Logger logger = Logger.getLogger(SpringAMQPTest.class);
    
    private static CachingConnectionFactory cachingConnectionFactory = null;
    
    static {
        try {
            System.out.println();
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
    
    /**
     * 使用spring-rabbit发送直连交换机类型的消息
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        logger.info(">>spring发送消息开始...");
        
        final String queueName = "MY_AMPQ_QUEUE";
        final String exchangeName = "MY_AMPQ_EXCHANGE";
        final String routingKey = "MY_AMPQ_ROUTING_KEY";
        final String msg = "Hello, world!";
        
        RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
        /**声明交换机，声明后即存在于rabbitmq服务端*/
        DirectExchange exchange = new DirectExchange(exchangeName);
        admin.declareExchange(exchange);
        /**声明队列*/
        Queue queue = new Queue(queueName);
        admin.declareQueue(queue);
        /**交换机和队列通过routingkey进行绑定*/
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
        /**发送消息*/
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        template.convertAndSend(exchangeName, routingKey, msg);
        logger.info(">>发送 ：" + msg + " 完毕\n");
        
        logger.info("接收消息开始，线程睡眠3s，避免延迟情况下获取不到消息");
        Thread.sleep(3000);
        Object obj = template.receiveAndConvert(queueName);
        if (null == obj) {
            logger.info(">> 未收取到");
        }
        logger.info(">> 收取 :" + String.valueOf(obj));
        
        cachingConnectionFactory.destroy();
        logger.info(">>连接已销毁.. ");
    }
}
