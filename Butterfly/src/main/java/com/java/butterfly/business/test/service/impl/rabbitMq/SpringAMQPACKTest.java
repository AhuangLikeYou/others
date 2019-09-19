package com.java.butterfly.business.test.service.impl.rabbitMq;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import com.rabbitmq.client.Channel;

/**
 * Created by lu.xu on 2017/8/22.
 * TODO: Message acknowledgment消息应答测试
 */
public class SpringAMQPACKTest {
    private static Logger logger = Logger.getLogger(SpringAMQPACKTest.class);
    
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
            ackTest();
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
     *Message acknowledgment消息应答测试
     * @throws Exception
     */
    public static void ackTest() throws Exception {
        System.out.println("\n\n\n");
        logger.info(">>测试ack开始..");
        
        final String exchangeName = "TEST_ACK_EXCHANGE";
        final String queueName = "TEST_ACK_QUEUENAME";
        final String routingKey = "TEST_ACK_ROUTINGKEY";
        
        DirectExchange exchange = new DirectExchange(exchangeName);
        Queue queue = new Queue(queueName);
        RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
        admin.declareExchange(exchange);
        admin.declareQueue(queue);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
        ;
        
        /**对exchange 和routingkey 发送消息*/
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        for (int i = 0; i < 5; i++) {
            String msg = "hello  ack " + i;
            System.out.println("发送:" + msg);
            template.convertAndSend(exchangeName, routingKey, msg);
        }
        logger.info("程序睡眠3s..");
        Thread.sleep(3000);
        RabbitAdmin consumerAdmin = new RabbitAdmin(cachingConnectionFactory);
        Integer size = (Integer) consumerAdmin.getQueueProperties(queueName).get("QUEUE_MESSAGE_COUNT");
        logger.info("当前队列大小:" + size);
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cachingConnectionFactory);
        container.setQueueNames(queueName);
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                System.out.println("接收 : " + new String(body));
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
            }
        });
        container.start();
        Thread.sleep(3000);
        container.stop();
    }
    
}
