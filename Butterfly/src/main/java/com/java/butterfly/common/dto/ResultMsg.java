package com.java.butterfly.common.dto;

/**
 * TODO(自定义消息对象，一般运用于返回操作结果以及附带信息)    .
 *
 * @author 许路
 * @version  v 1.0
 * @ClassName: ResultMsg
 * @date: 2016年4月18日 下午1:43:35
 */
public class ResultMsg {
    
    /** The success. */
    private boolean success = false; //是否操作成功,默认失败
    
    /** The status. */
    private String status; //操作状态 
    
    /** The msg. */
    private String msg; //附带消息
    
    /** The rut content. */
    private Object rutContent; //其他内容
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public Object getRutContent() {
        return rutContent;
    }
    
    public void setRutContent(Object rutContent) {
        this.rutContent = rutContent;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Instantiates a new ResultMsg.
     *
     * @param success the success
     * @param status the status
     * @param msg the msg
     * @param rutContent the rut content
     */
    public ResultMsg(boolean success, String status, String msg, Object rutContent) {
        super();
        this.success = success;
        this.status = status;
        this.msg = msg;
        this.rutContent = rutContent;
    }
    
    /**
     * Instantiates a new ResultMsg.
     *
     * @param success the success
     * @param msg the msg
     * @param rutContent the rut content
     */
    public ResultMsg(boolean success, String msg, Object rutContent) {
        super();
        this.success = success;
        this.msg = msg;
        this.rutContent = rutContent;
    }
    
    /**
     * Instantiates a new ResultMsg.
     *
     * @param success the success
     * @param msg the msg
     */
    public ResultMsg(boolean success, String msg) {
        super();
        this.success = success;
        this.msg = msg;
    }
    
    /**
     * Instantiates a new ResultMsg.
     */
    public ResultMsg() {
        super();
    }
    
    /**
     * Instantiates a new ResultMsg.
     *
     * @param success the success
     */
    public ResultMsg(boolean success) {
        super();
        this.success = success;
    }
    
    public ResultMsg(boolean success, Object rutContent) {
        super();
        this.success = success;
        this.rutContent = rutContent;
    }
    
}
