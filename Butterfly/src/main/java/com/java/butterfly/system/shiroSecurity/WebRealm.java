package com.java.butterfly.system.shiroSecurity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.java.butterfly.common.dto.CommonConstant;
import com.java.butterfly.system.ctrl.UserLoginController;
import com.java.butterfly.system.entity.SysUserInfo;
import com.java.butterfly.system.entity.SysUserResources;
import com.java.butterfly.system.service.IUserInfoService;
import com.java.butterfly.system.service.IUserResourcesService;

/**
 *自定义shiro-Realm类
 *
流程如下：
1、首先调用Subject.login(token)进行登录，其会自动委托给Security Manager
2、SecurityManager负责真正的身份验证逻辑；它会委托给Authenticator进行身份验证；
3、Authenticator才是真正的身份验证者，Shiro API中核心的身份认证入口点，此处可以自定义插入自己的实现,即此自定义Realm类
4、Authenticator可能会委托给相应的AuthenticationStrategy进行多Realm身份验证，
默认ModularRealmAuthenticator会调用AuthenticationStrategy进行多Realm身份验证
5、Authenticator会把相应的token传入Realm，从Realm获取身份验证信息，如果没有返回/抛出异常表示身份验证失败了。
此处可以配置多个Realm，将按照相应的顺序及策略进行访问。
 *
 * @ClassName: WebRealm
 * @author xulu
 * @date: 2017年4月29日 上午11:24:07
 * @version  v 1.0
 */
public class WebRealm extends AuthorizingRealm {
    
    private static Logger logger = Logger.getLogger(UserLoginController.class);
    
    @Resource
    protected IUserInfoService userinfoService;
    
    @Resource
    private IUserResourcesService iUserResourcesService;
    
    public WebRealm() {
        setName("WebRealm");
    }
    
    /**
     * 身份信息认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
        throws AuthenticationException {
        String username = (String) authcToken.getPrincipal();
        String password = new String((char[]) authcToken.getCredentials());
        logger.info(" >> shior用户认证开始 >> " + username);
        //身份验证在登录方法里面用自定义逻辑验证，此处默认发送到此请求中的用户名密码是合法的
        return new SimpleAuthenticationInfo(username, password, getName());
    }
    
    /**
     * 授权
     * 加载用户角色、权限信息
     * 使用了ecache，故不用担心每次读取db
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        logger.info(" >> shior权限认证开始>> " + username);
        //开始进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //草鸡管理员
        if (CommonConstant.ADMIN_USER_NAME.equals(username)) {
            logger.info(" >> shior权限认证，当前登录用户为超级管理员");
            info.addStringPermission("*");
        } else {
            SysUserInfo userinfo = this.userinfoService.queryByUserName(username);
            if (null == userinfo) {
                logger.info(" >> shior权限认证，用户信息数据为空，无法授权....");
                return null;
            }
            //去db获取用户角色&权限
            SysUserInfo sysUser = new SysUserInfo();
            sysUser.setUserName(username);
            sysUser.setUserId(userinfo.getUserId());
            List<SysUserResources> resourcesList = this.iUserResourcesService.loadUserResources(sysUser);
            if (null == resourcesList) {
                return null;
            }
            Set<String> permissions = new HashSet<String>();
            for (SysUserResources role : resourcesList) {
                //设置角色
                String roleId = String.valueOf(role.getRoleId());
                //                if (!info.getRoles().contains(roleId)) {
                info.addRole(roleId);
                //                }
                //设置权限,目前主要用于JSP界面标签
                permissions.add(String.valueOf(role.getResourceCode()));
            }
            info.setStringPermissions(permissions);
        }
        return info;
    }
    
}
