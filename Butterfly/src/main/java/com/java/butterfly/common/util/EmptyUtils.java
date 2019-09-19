package com.java.butterfly.common.util;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * TODO(判断是否为空工具类).
 *
 * @author 许路
 * @version v 1.0
 * @ClassName: EmptyUtils
 * @date: 2016年4月18日 下午2:08:26
 */
public class EmptyUtils {
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param colls the colls
     * @return boolean
     * @title: isEmpty
     * @date: 2016-5-20 15:11:47
     */
    //判断集合
    public static boolean isEmpty(Collection colls) {
        return null == colls || colls.isEmpty();
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param colls the colls
     * @return boolean
     * @title: isNotEmpty
     * @date: 2016-5-20 15:11:47
     */
    public static boolean isNotEmpty(Collection colls) {
        return !isEmpty(colls);
    }
    
    /**
     * 判断参数是否为空
     * @param args
     * @return true 为空/都为空；false 有任何一个不为空
     */
    public static boolean isEmpty(String... args) {
        if (null == args || args.length == 0) {
            return true;
        }
        boolean flag = true;
        for (String str : args) {
            flag = isEmpty(str);
            if (!flag) {
                break;
            }
        }
        return flag;
    }
    
    private static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }
    
    /**
     * 判断参数是否不为空
     * @param args 
     * @return ture 都不为空，false 有任何一个为空
     */
    public static boolean isNotEmpty(String... args) {
        if (null == args || args.length == 0) {
            return false;
        }
        for (String str : args) {
            if (isEmpty(str)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param l the l
     * @return boolean
     * @title: isEmpty
     * @date: 2016-5-20 15:11:47
     */
    //判断Long
    public static boolean isEmpty(Long l) {
        return null == l;
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param l the l
     * @return boolean
     * @title: isNotEmpty
     * @date: 2016-5-20 15:11:47
     */
    public static boolean isNotEmpty(Long l) {
        return !isEmpty(l);
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param map the map
     * @return boolean
     * @title: isEmpty
     * @date: 2016-5-20 15:11:47
     */
    //判断Map
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param map the map
     * @return boolean
     * @title: isNotEmpty
     * @date: 2016-5-20 15:11:47
     */
    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param obj the obj
     * @return boolean
     * @title: isEmpty
     * @date: 2016-5-20 15:11:47
     */
    //判断数组
    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length <= 0;
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param obj the obj
     * @return boolean
     * @title: isNotEmpty
     * @date: 2016-5-20 15:11:47
     */
    public static boolean isNotEmpty(Object[] obj) {
        return !isEmpty(obj);
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param date the date
     * @return boolean
     * @title: isEmpty
     * @date: 2016-5-20 15:11:47
     */
    //判断日期
    public static boolean isEmpty(Date date) {
        return date == null;
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param date the date
     * @return boolean
     * @title: isNotEmpty
     * @date: 2016-5-20 15:11:47
     */
    public static boolean isNotEmpty(Date date) {
        return !isEmpty(date);
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param itg the itg
     * @return boolean
     * @title: isEmpty
     * @date: 2016-5-20 15:11:47
     */
    //判断Integer
    public static boolean isEmpty(Integer itg) {
        return itg == null;
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param itg the itg
     * @return boolean
     * @title: isNotEmpty
     * @date: 2016-5-20 15:11:47
     */
    public static boolean isNotEmpty(Integer itg) {
        return !isEmpty(itg);
    }
    
    /**
     * 判断字符串是否为空白
     * 如 null, "", "   "都将返回true.   
     *
     * @author jiangqiubai
     * @param str the str
     * @return boolean
     * boolean 返回值
     * @Title: isTrimBlank
     */
    public static boolean isTrimBlank(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public static void main(String[] args) {
        String a = null;
        String b = "";
        String c = "c";
        String d = "d";
        System.out.println(isEmpty(a));
        System.out.println(isEmpty(a, b));
        System.out.println(isEmpty(a, b, c));
        System.out.println(isEmpty(c, d));
        System.out.println();
        System.out.println(isNotEmpty(a));
        System.out.println(isNotEmpty(a, b));
        System.out.println(isNotEmpty(a, b, c));
        System.out.println(isNotEmpty(c, d));
    }
}
