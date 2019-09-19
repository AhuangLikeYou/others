package com.java.butterfly.common.enums;

/**
 * Created by lu.xu on 2017/7/24.
 * TODO:
 */
public enum DataSourceEnums {
    DEFAULT_DATASOURCE_KEY("dataSource", "默认的数据源");
    
    private String value;
    
    private String valueCn;
    
    DataSourceEnums(String value, String valueCn) {
        this.value = value;
        this.valueCn = valueCn;
    }
    
    public String getValue() {
        return value;
    }
    
    public String getValueCn() {
        return valueCn;
    }
}
