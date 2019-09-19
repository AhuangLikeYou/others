package com.java.butterfly.business.test.service.impl.rabbitMq;

import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by lu.xu on 2017/8/22.
 * TODO: spring发送rabbit 多种exchange示例
 */
public class SpringAMQPExchangeTest {
    private static Logger logger = Logger.getLogger(SpringAMQPExchangeTest.class);
    
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
            directExchangeTest();
            headerExchangeTest();
            fanoutExchangeTest();
            topicExchangeTest();
            
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
     * Direct 直连交换机
     * 通过routingKey绑定交换机和队列
     */
    public static void directExchangeTest() throws Exception {
        System.out.println("\n\n\n");
        logger.info(">>开始往直连交换机上发送mq..");
        final String exchangeName = "TEST_DIRECT_EXCHANGE";
        final String queueName = "TEST_DIRECT_QUEUENAME1";
        final String routingKey = "TEST_DIRECT_ROUTINGKEY1";
        
        DirectExchange exchange = new DirectExchange(exchangeName);
        Queue queue = new Queue(queueName);
        RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
        admin.declareExchange(exchange);
        admin.declareQueue(queue);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        for (int i = 0; i < 5; i++) {
            /**发送的时候根据交换机名称，将路由名称传递，就能找到对应队列*/
            String msg = "hello direct " + i;
            System.out.println("发送:" + msg);
            template.convertAndSend(exchangeName, routingKey, msg);
        }
        logger.info(">>发送直连交换机队列消息完毕...\n");
        
        logger.info(">>直连交换机接收开始...");
        /**避免延迟情况下，统计队列大小出错*/
        Thread.sleep(3000);
        RabbitAdmin consumerAdmin = new RabbitAdmin(cachingConnectionFactory);
        Integer size = (Integer) consumerAdmin.getQueueProperties(queueName).get("QUEUE_MESSAGE_COUNT");
        logger.info("当前队列大小:" + size);
        RabbitTemplate consumerTemplate = new RabbitTemplate(cachingConnectionFactory);
        for (int i = 1; i <= size; i++) {
            Object obj = consumerTemplate.receiveAndConvert(queueName);
            System.out.println("接收消息：" + String.valueOf(obj));
        }
        logger.info(">>直连交换机接收完毕...");
        
    }
    
    /**
     *Headers 头交换机
     * 忽略routingKey，使用消息头内容建立路由规则
     *      通过判断消息头的值能否与指定的绑定相匹配来确立路由规则。
     * 使用：
     *      1.声明队列和exchange的绑定模式any or all 并且传递绑定规则
     *      2.发送消息时在消息头中明确绑定规则
     *注意：
     *      慎用头交换机，性能低
     */
    public static void headerExchangeTest() throws Exception {
        System.out.println("\n\n\n");
        logger.info(">>开始往头交换机上面发送消息...");
        final String exchangeName = "TEST_HEADERS_EXCHANGE";
        final String queueName1 = "TEST_HEADERS_QUEUENAME1";
        final String queueName2 = "TEST_HEADERS_QUEUENAME2";
        final String queueName3 = "TEST_HEADERS_QUEUENAME3";
        
        /**
         * 在一个交换机上面设置三个队列，并设置3个不同的路由规则
         */
        HeadersExchange exchange = new HeadersExchange(exchangeName);
        Queue queue1 = new Queue(queueName1);
        Queue queue2 = new Queue(queueName2);
        Queue queue3 = new Queue(queueName3);
        RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
        admin.declareExchange(exchange);
        admin.declareQueue(queue1);
        admin.declareQueue(queue2);
        admin.declareQueue(queue3);
        /**设置3个匹配规则*/
        Map header1 = new Hashtable();
        header1.put("name", "jack");
        header1.put("age", "20");
        Map header2 = new Hashtable();
        header2.put("sex", "sex");
        header2.put("temp", "temp");
        Map header3 = new Hashtable();
        header3.put("name", "jack");
        header3.put("sex", "sex");
        /**设置绑定模式和匹配规则*/
        admin.declareBinding(BindingBuilder.bind(queue1).to(exchange).whereAll(header1).match());
        admin.declareBinding(BindingBuilder.bind(queue2).to(exchange).whereAny(header2).match());
        admin.declareBinding(BindingBuilder.bind(queue3).to(exchange).whereAny(header3).match());
        
        /**
         *发送,发送前将消息头属性设置好
         * 队列1完全匹配，可以收到
         * 队列2模糊匹配，但是没有匹配项，收不到
         */
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("name", "jack");
        messageProperties.setHeader("age", "20");
        String msg =
            "hello  header 1，name jack ,age 20 ," + queue1.getName() + "、" + queue3.getName() + " will received";
        Message message = new Message(msg.getBytes(), messageProperties);
        System.out.println("发送: " + msg);
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        template.convertAndSend(exchangeName, null, message);
        /**
         *再发送一个,应该都可以收到
         */
        MessageProperties messageProperties2 = new MessageProperties();
        messageProperties2.setHeader("name", "jack");
        messageProperties2.setHeader("age", "20");
        messageProperties2.setHeader("sex", "sex");
        String msg2 = "hello  header 2，name jack ,age 20 ,sex sex , all queue will received";
        Message message2 = new Message(msg.getBytes(), messageProperties2);
        System.out.println("发送:" + msg2);
        template.convertAndSend(exchangeName, null, message2);
        logger.info(">>发送头交换机队列消息完毕...\n");
        
        logger.info(">>头交换机接收开始，线程睡眠3s避免延迟情况下，统计队列大小出错...");
        Thread.sleep(3000);
        RabbitAdmin consumerAdmin = new RabbitAdmin(cachingConnectionFactory);
        Integer size1 = (Integer) consumerAdmin.getQueueProperties(queueName1).get("QUEUE_MESSAGE_COUNT");
        Integer size2 = (Integer) consumerAdmin.getQueueProperties(queueName2).get("QUEUE_MESSAGE_COUNT");
        Integer size3 = (Integer) consumerAdmin.getQueueProperties(queueName3).get("QUEUE_MESSAGE_COUNT");
        logger.info(queueName1 + " 消费前大小 :" + size1);
        logger.info(queueName2 + " 消费前大小 :" + size2);
        logger.info(queueName3 + " 消费前大小 :" + size3);
        
        RabbitTemplate consumerTemplate = new RabbitTemplate(cachingConnectionFactory);
        System.out.println("\r");
        logger.info(">>收取 " + queue1.getName());
        for (int i = 1; i <= size1; i++) {
            Message mmsg = consumerTemplate.receive(queue1.getName());
            System.out.println(new String(mmsg.getBody()));
        }
        System.out.println("\r");
        logger.info(">>收取 " + queue2.getName());
        for (int i = 1; i <= size2; i++) {
            Message mmsg = consumerTemplate.receive(queue2.getName());
            System.out.println(new String(mmsg.getBody()));
        }
        System.out.println("\r");
        logger.info(">>收取 " + queue3.getName());
        for (int i = 1; i <= size3; i++) {
            Message mmsg = consumerTemplate.receive(queue3.getName());
            System.out.println(new String(mmsg.getBody()));
        }
        size1 = (Integer) consumerAdmin.getQueueProperties(queueName1).get("QUEUE_MESSAGE_COUNT");
        size2 = (Integer) consumerAdmin.getQueueProperties(queueName2).get("QUEUE_MESSAGE_COUNT");
        size3 = (Integer) consumerAdmin.getQueueProperties(queueName3).get("QUEUE_MESSAGE_COUNT");
        System.out.println("\r");
        logger.info(queueName1 + " 消费后大小 :" + size1);
        logger.info(queueName2 + " 消费后大小 :" + size2);
        logger.info(queueName3 + " 消费后大小 :" + size3);
        logger.info(">>头交换机接收完毕...");
        
    }
    
    /**
     *Fanout 扇形交换机
     *      忽略routingKey 当消息发送到交换机上，交换机会拷贝给它对应的所有队列
     */
    public static void fanoutExchangeTest() throws Exception {
        System.out.println("\n\n\n");
        logger.info(">>扇形交换机发送消息开始..");
        final String exchangeName = "TEST_FANOUT_EXCHANGE";
        final String queueName1 = "TEST_FANOUT_QUEUENAME1";
        final String queueName2 = "TEST_FANOUT_QUEUENAME2";
        final String queueName3 = "TEST_FANOUT_QUEUENAME3";
        
        FanoutExchange exchange = new FanoutExchange(exchangeName);
        Queue queue1 = new Queue(queueName1);
        Queue queue2 = new Queue(queueName2);
        Queue queue3 = new Queue(queueName3);
        /**交换机、队列进行绑定，不需要routingKey*/
        RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
        admin.declareExchange(exchange);
        admin.declareQueue(queue1);
        admin.declareBinding(BindingBuilder.bind(queue1).to(exchange));
        admin.declareQueue(queue2);
        admin.declareBinding(BindingBuilder.bind(queue2).to(exchange));
        admin.declareQueue(queue3);
        admin.declareBinding(BindingBuilder.bind(queue3).to(exchange));
        
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        for (int i = 0; i < 5; i++) {
            String msg = "hello  fanout " + i;
            logger.info("发送：" + msg);
            template.convertAndSend(exchangeName, null, msg);
        }
        logger.info(">>发送扇形交换机队列消息完毕...");
        
        logger.info(">>扇形换机接收开始，程序睡眠3s避免延迟情况下，统计队列大小出错...");
        Thread.sleep(3000);
        RabbitAdmin consumerAdmin = new RabbitAdmin(cachingConnectionFactory);
        Integer size1 = (Integer) consumerAdmin.getQueueProperties(queueName1).get("QUEUE_MESSAGE_COUNT");
        Integer size2 = (Integer) consumerAdmin.getQueueProperties(queueName2).get("QUEUE_MESSAGE_COUNT");
        Integer size3 = (Integer) consumerAdmin.getQueueProperties(queueName3).get("QUEUE_MESSAGE_COUNT");
        logger.info(queueName1 + "当前队列大小:" + size1);
        logger.info(queueName2 + "当前队列大小:" + size2);
        logger.info(queueName3 + "当前队列大小:" + size3);
        
        RabbitTemplate consumerTemplate = new RabbitTemplate(cachingConnectionFactory);
        System.out.println("\r");
        logger.info(">>接收 " + queue1.getName());
        for (int i = 1; i <= size1; i++) {
            Message mmsg = consumerTemplate.receive(queue1.getName());
            System.out.println(new String(mmsg.getBody()));
        }
        System.out.println("\r");
        logger.info(">>接收 " + queue2.getName());
        for (int i = 1; i <= size2; i++) {
            Message mmsg = consumerTemplate.receive(queue2.getName());
            System.out.println(new String(mmsg.getBody()));
        }
        System.out.println("\r");
        logger.info(">>接收 " + queue3.getName());
        for (int i = 1; i <= size3; i++) {
            Message mmsg = consumerTemplate.receive(queue3.getName());
            System.out.println(new String(mmsg.getBody()));
        }
        logger.info(">>扇形交换机接收完毕...");
        
    }
    
    /**
     *topic  主题交换机
     *      routingKey 和队列进行模糊匹配
     *      Exchange没有发现能够与RouteKey匹配的Queue，则会抛弃此消息
     *      
     * 匹配关键字 #和* , “#”表示0个或若干个关键字，“*”表示一个关键字
     * 例如：
     *  log.test.test 可以匹配 log.# ,但是不能匹配log.*
     *  log.test 可以匹配log.*
     */
    public static void topicExchangeTest() throws Exception {
        System.out.println("\n\n\n");
        logger.info(">>主题交换机发送消息开始...");
        final String exchangeName = "TEST_TOPIC_EXCHANGE";
        final String queueName1 = "TEST_TOPIC_QUEUENAME1";
        final String queueName2 = "TEST_TOPIC_QUEUENAME2";
        final String routingKey1 = "#.RTK.#";
        final String routingKey2 = "*.RTK.*";
        
        TopicExchange exchange = new TopicExchange(exchangeName);
        Queue queue1 = new Queue(queueName1);
        Queue queue2 = new Queue(queueName2);
        RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
        admin.declareExchange(exchange);
        admin.declareQueue(queue1);
        admin.declareBinding(BindingBuilder.bind(queue1).to(exchange).with(routingKey1));
        admin.declareQueue(queue2);
        admin.declareBinding(BindingBuilder.bind(queue2).to(exchange).with(routingKey2));
        /**发送*/
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        String msg = "  hello topic for all";
        logger.info("给所有队列发送：" + msg);
        template.convertAndSend(exchangeName, "test.RTK.test", msg);
        msg = "  hello topic for none";
        logger.info("发送一个无法匹配的队列：" + msg);
        template.convertAndSend(exchangeName, "test.test.RRRRTTTK.test.test", msg);
        msg = "  hello topic for " + queueName1;
        logger.info("发送一个" + queueName1 + "可以接收到的：" + msg);
        template.convertAndSend(exchangeName, "test.test.RTK.test.test", msg);
        logger.info(">>发送主题交换机队列消息完毕...");
        
        logger.info(">>主题交换机接收开始，程序睡眠3s，避免延迟情况下，统计队列大小出错...");
        Thread.sleep(3000);
        RabbitAdmin consumerAdmin = new RabbitAdmin(cachingConnectionFactory);
        Integer size1 = (Integer) consumerAdmin.getQueueProperties(queueName1).get("QUEUE_MESSAGE_COUNT");
        Integer size2 = (Integer) consumerAdmin.getQueueProperties(queueName2).get("QUEUE_MESSAGE_COUNT");
        logger.info(queueName1 + "当前队列大小:" + size1);
        logger.info(queueName2 + "当前队列大小:" + size2);
        
        RabbitTemplate consumerTemplate = new RabbitTemplate(cachingConnectionFactory);
        System.out.println("\r");
        logger.info(">>接收 " + queue1.getName());
        for (int i = 1; i <= size1; i++) {
            Message mmsg = consumerTemplate.receive(queue1.getName());
            System.out.println(new String(mmsg.getBody()));
        }
        System.out.println("\r");
        logger.info(">>接收 " + queue2.getName());
        for (int i = 1; i <= size2; i++) {
            Message mmsg = consumerTemplate.receive(queue2.getName());
            System.out.println(new String(mmsg.getBody()));
        }
        logger.info(">>主题交换机接收完毕...");
    }
}
