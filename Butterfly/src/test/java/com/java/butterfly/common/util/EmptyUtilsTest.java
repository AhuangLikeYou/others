package com.java.butterfly.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by lu.xu on 2017/8/10.
 * TODO:
 */
public class EmptyUtilsTest {
    @Before
    public void showMsgBefore() {
        System.out.println("***********EmptyUtilsTest start**********");
    }
    
    @Test
    public void main() {
        this.collection();
        this.date();
        this.integer();
        this.longT();
        this.map();
        this.array();
        this.strings();
        this.blank();
    }
    
    public void collection() {
        System.out.println("-----collection start----");
        List list = null;
        Assert.assertTrue(EmptyUtils.isEmpty(list));
        Assert.assertFalse(EmptyUtils.isNotEmpty(list));
        list = new ArrayList<>();
        Assert.assertTrue(EmptyUtils.isEmpty(list));
        Assert.assertFalse(EmptyUtils.isNotEmpty(list));
        list.add("test");
        Assert.assertFalse(EmptyUtils.isEmpty(list));
        Assert.assertTrue(EmptyUtils.isNotEmpty(list));
        System.out.println("-----collection end ----");
    }
    
    public void date() {
        System.out.println("-----date start----");
        Date date = null;
        Assert.assertTrue(EmptyUtils.isEmpty(date));
        Assert.assertFalse(EmptyUtils.isNotEmpty(date));
        date = new Date();
        Assert.assertFalse(EmptyUtils.isEmpty(date));
        Assert.assertTrue(EmptyUtils.isNotEmpty(date));
        System.out.println("-----date end----");
    }
    
    public void integer() {
        System.out.println("-----integer start----");
        Integer integer = null;
        Assert.assertTrue(EmptyUtils.isEmpty(integer));
        Assert.assertFalse(EmptyUtils.isNotEmpty(integer));
        integer = new Integer(10);
        Assert.assertFalse(EmptyUtils.isEmpty(integer));
        Assert.assertTrue(EmptyUtils.isNotEmpty(integer));
        System.out.println("-----integer end----");
    }
    
    public void longT() {
        System.out.println("-----longT start----");
        Long lon = null;
        Assert.assertTrue(EmptyUtils.isEmpty(lon));
        Assert.assertFalse(EmptyUtils.isNotEmpty(lon));
        lon = new Long(10);
        Assert.assertFalse(EmptyUtils.isEmpty(lon));
        Assert.assertTrue(EmptyUtils.isNotEmpty(lon));
        System.out.println("-----longT end----");
    }
    
    public void map() {
        System.out.println("-----map start----");
        Map map = null;
        Assert.assertTrue(EmptyUtils.isEmpty(map));
        Assert.assertFalse(EmptyUtils.isNotEmpty(map));
        map = new HashMap();
        Assert.assertTrue(EmptyUtils.isEmpty(map));
        Assert.assertFalse(EmptyUtils.isNotEmpty(map));
        map.put("1", 1);
        Assert.assertFalse(EmptyUtils.isEmpty(map));
        Assert.assertTrue(EmptyUtils.isNotEmpty(map));
        System.out.println("-----map end----");
    }
    
    public void array() {
        System.out.println("-----array start----");
        String[] str = null;
        Assert.assertTrue(EmptyUtils.isEmpty(str));
        Assert.assertFalse(EmptyUtils.isNotEmpty(str));
        str = new String[] {};
        Assert.assertTrue(EmptyUtils.isEmpty(str));
        Assert.assertFalse(EmptyUtils.isNotEmpty(str));
        str = new String[] {""};
        Assert.assertTrue(EmptyUtils.isEmpty(str));
        Assert.assertFalse(EmptyUtils.isNotEmpty(str));
        System.out.println("-----array end----");
    }
    
    public void strings() {
        System.out.println("-----strings start----");
        String str1 = null, str2 = null;
        Assert.assertTrue(EmptyUtils.isEmpty(str1));
        Assert.assertFalse(EmptyUtils.isNotEmpty(str1));
        Assert.assertTrue(EmptyUtils.isEmpty(str1, str2));
        Assert.assertFalse(EmptyUtils.isNotEmpty(str1, str2));
        str2 = "test";
        Assert.assertFalse(EmptyUtils.isNotEmpty(str1, str2));
        str1 = str2 = "";
        Assert.assertTrue(EmptyUtils.isEmpty(str1));
        Assert.assertFalse(EmptyUtils.isNotEmpty(str1));
        Assert.assertTrue(EmptyUtils.isEmpty(str1, str2));
        Assert.assertFalse(EmptyUtils.isNotEmpty(str1, str2));
        str2 = "test";
        Assert.assertFalse(EmptyUtils.isNotEmpty(str1, str2));
        str1 = str2 = "test";
        Assert.assertFalse(EmptyUtils.isEmpty(str1));
        Assert.assertFalse(EmptyUtils.isEmpty(str1, str2));
        Assert.assertTrue(EmptyUtils.isNotEmpty(str1));
        Assert.assertTrue(EmptyUtils.isNotEmpty(str1, str2));
        System.out.println("-----strings end----");
    }
    
    public void blank() {
        System.out.println("-----blank start----");
        String str = null;
        Assert.assertTrue(EmptyUtils.isTrimBlank(str));
        str = "";
        Assert.assertTrue(EmptyUtils.isTrimBlank(str));
        str = " ";
        Assert.assertTrue(EmptyUtils.isTrimBlank(str));
        str = "    ";
        Assert.assertTrue(EmptyUtils.isTrimBlank(str));
        str = "test";
        Assert.assertFalse(EmptyUtils.isTrimBlank(str));
        str = "test ";
        Assert.assertFalse(EmptyUtils.isTrimBlank(str));
        str = "test  ";
        Assert.assertFalse(EmptyUtils.isTrimBlank(str));
        System.out.println("-----blank end----");
    }
    
    @After
    public void showMsgAfter() {
        System.out.println("***********EmptyUtilsTest end**********");
    }
}
