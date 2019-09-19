package com.java.butterfly.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.java.butterfly.common.dto.PageInfo;

/**
 * TODO(数据收集器，根据request获取数据)    
 * 使用方式：  UserInfoDTO userinfo = (UserInfoDTO) DataCollecter.collectionData(request, UserInfoDTO.class);
 * @ClassName: DataCollecter    
 * @date: 2016年5月24日 下午7:29:13    
 * @version  v 1.0
 */
public class DataCollecter {
    private static Logger logger = Logger.getLogger(DataCollecter.class);
    
    public static Object collectionData(HttpServletRequest request, Class<?> classes) throws Exception {
        Object resultObj = null;
        try {
            Class<?> mob = Class.forName(classes.getName());
            resultObj = mob.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据收集器实例化对象出现错误！");
            return null;
        }
        Map parameterMap = request.getParameterMap();
        //获取需要组装的对象所有的方法(包括publuc、private、protected 但是父类的获取不了)
        Method[] formMethod = classes.getDeclaredMethods();
        //所有的属性
        Field[] fields = classes.getDeclaredFields();
        if (EmptyUtils.isEmpty(fields)) {
            return classes;
        }
        String fieldsName = null;
        String setMethodName = null;
        Method setMethod = null;
        for (int i = 0; i < fields.length; i++) {
            fieldsName = fields[i].getName();
            setMethodName = "set" + fieldsName.substring(0, 1).toUpperCase() + fieldsName.substring(1);
            //前台未传递
            if (null == parameterMap.get(fieldsName)) {
                continue;
            }
            //获取前台传递值
            String parameterVal = ((String[]) parameterMap.get(fieldsName))[0];
            setMethod = findMethodByName(formMethod, setMethodName); //获取set方法
            if (null == setMethod) {
                continue;
            }
            Object setValue = getValueByFieldType(fields[i].getType(), parameterVal);
            if (null == setValue) {
                continue;
            }
            try {
                //赋值
                setMethod.invoke(resultObj, setValue);
            } catch (Exception e) {
                logger.info("\n**************DataCollector  error info start*************");
                logger.info("field:" + fields[i]);
                logger.info("列名：" + fieldsName);
                logger.info("类型: " + fields[i].getType());
                logger.info("值：" + parameterVal);
                logger.info("**************DataCollector error info over*************\n");
                throw e;
            }
        }
        //        return resultObj;
        //        20170109针对方法进行扩展
        return collectionDataExtended(resultObj, request, classes);
    }
    
    /**
     * TODO(collectionData方法扩展
        ，判断类是否是X的子类，然后可以对父类成员变量赋值)    
     * @author 许路    
     * @date: 2017年1月11日 上午10:19:35
     * @Title: collectionDataExtended    
     * @param resultObj
     * @param request
     * @param classes
     * @return
     * @throws Exception Object 返回值
     */
    public static Object collectionDataExtended(Object resultObj, HttpServletRequest request, Class<?> classes)
        throws Exception {
        if (PageInfo.class.isAssignableFrom(resultObj.getClass())) {
            logger.info("Datacollecter 检测到对象是PageInfo子类，开始对父类分页信息进行赋值.....");
            Map parameterMap = request.getParameterMap();
            //所有的public方法，包含父类
            Method[] formMethod = classes.getMethods();
            //所有的public属性，包含父类
            Field[] fields = classes.getFields();
            String fieldsName = null;
            String setMethodName = null;
            Method setMethod = null;
            for (int i = 0; i < fields.length; i++) {
                fieldsName = fields[i].getName();
                if (!"pageNo".equals(fieldsName) && !"pageSize".equals(fieldsName)) {
                    continue;
                }
                setMethodName = "set" + fieldsName.substring(0, 1).toUpperCase() + fieldsName.substring(1);
                //前台未传递
                if (null == parameterMap.get(fieldsName)) {
                    continue;
                }
                //获取前台传递值
                String parameterVal = ((String[]) parameterMap.get(fieldsName))[0];
                setMethod = findMethodByName(formMethod, setMethodName); //获取set方法
                if (null == setMethod) {
                    continue;
                }
                Object setValue = getValueByFieldType(fields[i].getType(), parameterVal);
                if (null == setValue) {
                    continue;
                }
                try {
                    //赋值
                    setMethod.invoke(resultObj, setValue);
                } catch (Exception e) {
                    logger.info("\n**************DataCollector  error info start*************");
                    logger.info("field:" + fields[i]);
                    logger.info("列名：" + fieldsName);
                    logger.info("类型: " + fields[i].getType());
                    logger.info("值：" + parameterVal);
                    logger.info("**************DataCollector error info over*************\n");
                    throw e;
                }
            }
        }
        return resultObj;
    }
    
    /**
     * TODO(获取方法)    
     * @author 许路    
     * @date: 2016年5月25日 上午9:18:21
     * @Title: findMethodByName    
     * @param methods
     * @param name
     * @return Method 返回值
     */
    private static Method findMethodByName(Method[] methods, String name) {
        for (int j = 0; j < methods.length; j++) {
            if (methods[j].getName().equals(name)) {
                return methods[j];
            }
        }
        return null;
    }
    
    /**
     * TODO(根据列的类型以及列的值，返回一个对应类型的新的值)    
     * @author 许路    
     * @date: 2016年5月25日 上午9:17:31
     * @Title: getValueByFieldType    
     * @param fieldType
     * @param value
     * @return
     * @throws ParseException Object 返回值
     */
    //根据属性返回值
    private static Object getValueByFieldType(Object fieldType, String value) throws ParseException {
        if (EmptyUtils.isEmpty(value)) {
            return null;
        } else if (fieldType instanceof Integer) {
            return Integer.valueOf(value);
        } else if (fieldType instanceof String) {
            return value;
        } else if (fieldType instanceof Long) {
            return Long.valueOf(value);
        } else if (fieldType instanceof Byte) {
            return value.getBytes();
        } else if (fieldType instanceof Boolean) {
            if ("TRUE".equals(value.toUpperCase())) {
                return true;
            }
            return false;
        } else if (fieldType instanceof Short) {
            return Short.valueOf(value);
        } else if (fieldType instanceof Character) {
            return new Character(value.toCharArray()[0]);
        } else if (fieldType instanceof Float) {
            return Float.valueOf(value);
        } else if (fieldType instanceof Double) {
            return Double.valueOf(value);
        } else if (fieldType instanceof Date) {
            if (!value.contains(":")) {
                return BeanUtils.parseStringToDate(value + " 00:00:00");
            }
            return BeanUtils.parseStringToDate(value);
        } else if ("class java.lang.Integer".equals(fieldType.toString())) {
            return Integer.valueOf(value);
        } else if ("class java.lang.String".equals(fieldType.toString())) {
            return value;
        } else if ("class java.lang.Long".equals(fieldType.toString())) {
            return Long.valueOf(value);
        } else if ("class [B".equals(fieldType.toString())) {
            return value.getBytes();
        } else if ("class [Ljava.lang.Byte;".equals(fieldType.toString())) {
            byte[] b = value.getBytes();
            Byte[] B = new Byte[b.length];
            for (int i = 0; i < b.length; i++) {
                B[i] = Byte.valueOf(b[i]);
            }
            return B;
        } else if ("class java.lang.Boolean".equals(fieldType.toString())) {
            if ("TRUE".equals(value.toUpperCase())) {
                return true;
            }
            return false;
        } else if ("class java.lang.Short".equals(fieldType.toString())) {
            return Short.valueOf(value);
        } else if ("class java.lang.Character".equals(fieldType.toString())) {
            return new Character(value.toCharArray()[0]);
        } else if ("class java.lang.Float".equals(fieldType.toString())) {
            return Float.valueOf(value);
        } else if ("class java.lang.Double".equals(fieldType.toString())) {
            return Double.valueOf(value);
        } else if ("class java.util.Date".equals(fieldType.toString())) {
            if (!value.contains(":")) {
                return BeanUtils.parseStringToDate(value + " 00:00:00");
            }
            return BeanUtils.parseStringToDate(value);
        } else if ("int".equals(fieldType.toString())) {
            return Integer.valueOf(value).intValue();
        } else if ("double".equals(fieldType.toString())) {
            return Double.valueOf(value).doubleValue();
        } else if ("long".equals(fieldType.toString())) {
            return Long.valueOf(value).longValue();
        } else if ("byte".equals(fieldType.toString())) {
            return value.getBytes();
        } else if ("boolean".equals(fieldType.toString())) {
            if ("TRUE".equals(value.toUpperCase())) {
                return true;
            }
            return false;
        } else if ("short".equals(fieldType.toString())) {
            return Short.valueOf(value).shortValue();
        } else if ("char".equals(fieldType.toString())) {
            return value.toCharArray()[0];
        } else if ("double".equals(fieldType.toString())) {
            return Double.valueOf(value).doubleValue();
        } else if ("float".equals(fieldType.toString())) {
            return Float.valueOf(value).floatValue();
        } else {
            return value;
        }
    }
}
