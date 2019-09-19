package com.java.butterfly.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO(组装前台tabel数据格式)    
 * @ClassName: TabelData    
 * @author xl    
 * @date 2017年1月13日 下午3:15:44    
 * @version  v 1.0
 */
public class TabelData {
    public static Map<String, Object> tabelDataResult(Integer total, Object rows) {
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", rows);
        return map;
    }
}
