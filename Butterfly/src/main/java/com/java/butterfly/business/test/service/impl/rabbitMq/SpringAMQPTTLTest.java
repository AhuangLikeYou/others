package com.java.butterfly.business.test.service.impl.rabbitMq;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by lu.xu on 2017/8/22.
 * TODO:TTL:Time-To-Live Extensions RabbitMQ消息过期时间 测试
 */
public class SpringAMQPTTLTest {
    private static Logger logger = Logger.getLogger(SpringAMQPTTLTest.class);
    
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
            testTTLForQueueMsg();
            testTTLForSingleMsg();
            testTTLForQueue();
            
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
     *TODO:TTL:Time-To-Live Extensions RabbitMQ消息过期时间 测试
     * 对队列中所有的设置过期时间
     * Per-Queue Message TTL
     */
    public static void testTTLForQueueMsg() throws Exception {
        System.out.println("\n\n\n");
        logger.info(">>ttl 队列测试开始..");
        final String exchangeName = "TEST_TTL_EXCHANGE";
        final String queueName = "TEST_TLT_QUEUENAME";
        final String routingKey = "TEST_TTL_ROUTINGKEY";
        
        DirectExchange exchange = new DirectExchange(exchangeName);
        /**这设置过期时间*/
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 5000);
        Queue queue = new Queue(queueName, false, false, false, arguments);
        
        RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
        admin.declareExchange(exchange);
        admin.declareQueue(queue);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
        
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
        
        logger.info(">>ttl 队列测试完毕...");
        
    }
    
    /***
     * TODO:单条消息设置过期时间
     * 注意！！即使消息过期，也不会马上从队列中抹去
     * @throws Exception
     */
    public static void testTTLForSingleMsg() throws Exception {
        System.out.println("\n\n\n");
        logger.info(">>ttl消息开始..");
        final String exchangeName = "TEST_TTL_EXCHANGE";
        final String queueName = "TEST_TLT_QUEUENAME";
        final String routingKey = "TEST_TTL_ROUTINGKEY";
        
        DirectExchange exchange = new DirectExchange(exchangeName, false, false);
        Queue queue = new Queue(queueName, false, false, false);
        
        RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
        admin.declareExchange(exchange);
        admin.declareQueue(queue);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
        
        /**发送*/
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        for (int i = 0; i < 5; i++) {
            String msg = "hello ttl " + i;
            System.out.println("发送:" + msg);
            template.convertAndSend(exchangeName, routingKey, msg);
        }
        
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
        messageProperties.setExpiration("3000");
        String msg = "测试过期时间";
        Message message = new Message(msg.getBytes(), messageProperties);
        template.convertAndSend(exchangeName, routingKey, message);
        
        logger.info(">>发送完毕开始计算大小...");
        /***
         * 对于第一种设置队列TTL属性的方法，一旦消息过期，就会从队列中抹去，
         * 而第二种方法里，即使消息过期，也不会马上从队列中抹去，
         * 因为每条消息是否过期时在即将投递到消费者之前判定的，为什么两者得处理方法不一致？
         * 因为第一种方法里，队列中已过期的消息肯定在队列头部，RabbitMQ只要定期从队头开始扫描是否有过期消息即可，
         * 而第二种方法里，每条消息的过期时间不同，如果要删除所有过期消息，势必要扫描整个队列，
         * 所以不如等到此消息即将被消费时再判定是否过期，如果过期，再进行删除
         */
        Thread.sleep(3000);
        RabbitAdmin consumerAdmin = new RabbitAdmin(cachingConnectionFactory);
        Integer size = (Integer) consumerAdmin.getQueueProperties(queueName).get("QUEUE_MESSAGE_COUNT");
        logger.info(">>过期时间过前，队列大小：" + size);
        Thread.sleep(3000);
        size = (Integer) consumerAdmin.getQueueProperties(queueName).get("QUEUE_MESSAGE_COUNT");
        logger.info(">>过期时间过后，队列大小：" + size);
        
        logger.info(">>ttl 消息测试完毕...");
        
    }
    
    /***
     * Queue TTL
     * TODO:设置队列过期时间,而不是队列中的消息，队列过期后会自动被删除
     * queue.declare 命令中的 x-expires 参数控制 queue 被自动删除前可以处于未使用状态的时间。
     * 未使用的意思是 queue上没有任何 consumer ，queue 没有被重新声明，并且在过期时间段内未调用过 basic.get 命令。
     * 该方式可用于，例如，RPC-style 的回复 queue ，其中许多 queue 会被创建出来，但是却从未被使用。
     * @throws Exception
     */
    
    public static void testTTLForQueue() throws Exception {
        System.out.println("\n\n\n");
        logger.info(">>ttl 队列测试开始..");
        final String exchangeName = "TEST_TTL_EXCHANGE";
        final String queueName = "TEST_TLT_QUEUENAME";
        final String routingKey = "TEST_TTL_ROUTINGKEY";
        
        DirectExchange exchange = new DirectExchange(exchangeName);
        /**这设置过期时间*/
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-expires", 5000);
        Queue queue = new Queue(queueName, false, false, false, arguments);
        
        RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
        admin.declareExchange(exchange);
        admin.declareQueue(queue);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
        
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
        Thread.sleep(10000);
        size = (Integer) consumerAdmin.getQueueProperties(queueName).get("QUEUE_MESSAGE_COUNT");
        logger.info(">>过期时间过后，队列大小：" + size);
        
        logger.info(">>ttl 队列测试完毕...");
        
    }
    
}
