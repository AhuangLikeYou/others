package com.test;

import com.alibaba.fastjson.JSONObject;
import io.github.hengyunabc.zabbix.api.DefaultZabbixApi;
import io.github.hengyunabc.zabbix.api.Request;
import io.github.hengyunabc.zabbix.api.RequestBuilder;
import org.apache.log4j.Logger;


/**
 * Created by lu.xu on 2017/7/4.
 * TODO: zabbix操作类
 */
public class ZabbixUtil {
    private static Logger logger = Logger.getLogger(ZabbixUtil.class);
    /**
     * zabbix api路径
     */
    private static final String ZABBIX_URL = "http://10.40.1.26/zabbix/api_jsonrpc.php";
     
    /**
     * zabbix用户名
     */
    private static final String ZABBIX_USERNAME = "Admin";
    /**
     * zabbix密码
     */
    private static final String ZABBIX_PWD = "Admin";
    
    /**
     *登陆后获取的认证
     *操作zabbix，如果没有登录，需要获取认证字符串
     *如果有认证后，每次操作传递认证即可，不用再次登录
     */
    private static volatile String auth = null;
    
    
    /**
     * 获取认证
     */
    public static void getAuth() throws Exception {
        if (EmptyUtils.isEmpty(auth)) {
            synchronized (new Object()) {
                if (EmptyUtils.isNotEmpty(auth)) {
                    return;
                }
                logger.info(">> 获取auth..");
                /**
                 * 登录并获取认证
                 */
                Request getRequest = RequestBuilder.newBuilder()
                    .paramEntry("user", ZABBIX_USERNAME)
                    .paramEntry("password", ZABBIX_PWD)
                    .method("user.login")
                    .build();
                DefaultZabbixApi zabbixApi = new DefaultZabbixApi(ZABBIX_URL);
                zabbixApi.init();
                JSONObject getResponse = zabbixApi.call(getRequest);
                /**
                 * 解析认证编码
                 */
                logger.info(getResponse);
                if (null != getResponse && null != getResponse.get("result")) {
                    auth = String.valueOf(getResponse.get("result"));
                } else {
                    throw new Exception("获取认证失败");
                }
            }
        }
    }
    
    /**
     * 退出zabbix
     */
    public static void logOut() throws Exception {
        logger.info(">> zabbix退出登录..");
        Request getRequest = RequestBuilder.newBuilder().auth(auth).method("user.logOut").build();
        JSONObject getResponse = call(getRequest);
        logger.info(getResponse);
        auth = null;
    }
    
    /**
     * 封装调用方法
     * @param request
     * @return
     */
    public static JSONObject call(Request request) throws Exception {
        //获取认证
        getAuth();
        //添加认证参数
        request.setAuth(auth);
        //调用zabbix接口并返回数据
        DefaultZabbixApi zabbixApi = new DefaultZabbixApi(ZABBIX_URL);
        zabbixApi.init();
        return zabbixApi.call(request);
    }
     
    
    /**
     * 将zabbix 这个little bitch 返回的timestamp转换为日期
     * @param timestamp
     * @return
     */
    public static String pareTimestamp(String timestamp) {
        if (EmptyUtils.isEmpty(timestamp)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (timestamp.length() == 10) {
            return sdf.format(new Date(Long.valueOf(timestamp + "000")));
        } else if (timestamp.length() == 13) {
            return sdf.format(new Date(Long.valueOf(timestamp)));
        } else {
            return null;
        }
    }
}
