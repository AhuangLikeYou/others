package com.java.butterfly.business.test.dto;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by lu.xu on 2017/8/17.
 * TODO: 传递 MQ 消息
 */
public class MQmessageDTO implements Serializable {
    private String exchangeName;
    
    private String queenName;
    
    private byte[] msgContent;
    
    public String getExchangeName() {
        return exchangeName;
    }
    
    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }
    
    public String getQueenName() {
        return queenName;
    }
    
    public void setQueenName(String queenName) {
        this.queenName = queenName;
    }
    
    public byte[] getMsgContent() {
        return msgContent;
    }
    
    public void setMsgContent(byte[] msgContent) {
        this.msgContent = msgContent;
    }
    
    @Override
    public String toString() {
        return "EopEventMessage [queueName=" + queenName + ", exchangeName=" + exchangeName + ", eventData="
            + Arrays.toString(msgContent) + "]";
    }
    
}
