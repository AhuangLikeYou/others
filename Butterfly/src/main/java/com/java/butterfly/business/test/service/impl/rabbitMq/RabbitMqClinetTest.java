package com.java.butterfly.business.test.service.impl.rabbitMq;

import org.apache.log4j.Logger;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

/**
 * Created by lu.xu on 2017/8/22.
 * TODO: rabbitClint 发送接收MQ
 */
public class RabbitMqClinetTest {
    private static Logger logger = Logger.getLogger(RabbitMqClinetTest.class);
    
    private static ConnectionFactory rabbitCf = null;
    static {
        try {
            rabbitCf = new ConnectionFactory();
            rabbitCf.setHost(MqPropertiesUtil.MQ_HOST);
            rabbitCf.setPort(MqPropertiesUtil.MQ_PORT);
            rabbitCf.setUsername(MqPropertiesUtil.MQ_USER);
            rabbitCf.setPassword(MqPropertiesUtil.MQ_PWD);
            rabbitCf.setConnectionTimeout(MqPropertiesUtil.MQ_TIME_OUT);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    /**
     * 使用rabbitmq-client实现发送消息
     *      不指定exchange类型，也不指定routingkey 使用默认的
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        logger.info(">> 发送消息开始...");
        Connection connection = rabbitCf.newConnection();
        Channel channel = connection.createChannel();
        
        String QUEUE_NAME = "RABBIT_QUEUE";
        String message = "Hello World!";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        
        /**
         * 踩坑！
         * 如果发现程序走到：  channel.basicPublish就阻塞了，后来吧rabbitmq重启了就好了
         * 重启之前 rabbitmqctl status只能看到 rabbitmq在哪台机器
         * 重启之后 同样的命令能看到各种信息
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        logger.info(" 发送 ：" + message + " 完毕\n");
        
        logger.info("接收消息开始，线程睡眠3s，避免延迟情况下获取不到消息");
        Thread.sleep(3000);
        GetResponse response = channel.basicGet(QUEUE_NAME, false);
        if (response == null) {
            logger.info("未收到到...");
        } else {
            AMQP.BasicProperties props = response.getProps();
            byte[] body = response.getBody();
            String msg = new String(body);
            logger.info("收取消息:" + msg);
            long deliveryTag = response.getEnvelope().getDeliveryTag();
            channel.basicAck(response.getEnvelope().getDeliveryTag(), false); // acknowledge receipt of the message
        }
        logger.info("接收消息完毕..");
        channel.close();
        
        connection.close();
        logger.info(">>连接已销毁.. ");
    }
    
}
