package com.java.butterfly.common.dto;

// TODO: Auto-generated Javadoc
/**
 * TODO(界面分页信息)    .
 *
 * @author 许路
 * @version  v 1.0
 * @ClassName: PageInfo
 * @date: 2017年1月6日 上午11:37:58
 */
public class PageInfo {
    
    public static final String LIMIT_FIELD_NAME = "limit";
    
    public static final String OFFSET_FIELD_NAME = "offset";
    
    /** 
     * 为了直接对应bootstrap-table
     * offset：分页起始值
     * limit：本页显示量
     */
    private int offset = 0;
    
    private int limit = 10;
    
    public int getLimit() {
        return limit;
    }
    
    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    public int getOffset() {
        return offset;
    }
    
    public void setOffset(int offset) {
        this.offset = offset;
    }
    
}