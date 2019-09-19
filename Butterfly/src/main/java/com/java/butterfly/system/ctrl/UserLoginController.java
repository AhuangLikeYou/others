
package com.java.butterfly.system.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.java.butterfly.system.dto.ResetPwdDTO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.butterfly.common.dto.CommonConstant;
import com.java.butterfly.common.dto.ResultMsg;
import com.java.butterfly.common.util.ShiroUtil;
import com.java.butterfly.common.util.encrypt.Md5Encryption;
import com.java.butterfly.system.entity.SysUserInfo;
import com.java.butterfly.system.service.IUserLoginService;
import com.java.butterfly.system.service.IUserResourcesService;

/**
 * TODO(用户登录)    
 * @ClassName: UserLoginController    
 * @author xl    
 * @date 2017年2月21日 上午9:45:55    
 * @version  v 1.0
 */
@Controller
@RequestMapping(value = "/system/userLogin")
public class UserLoginController {
    private static Logger logger = Logger.getLogger(UserLoginController.class);
    
    @Resource
    private IUserLoginService iuserloginService;
    
    @Resource
    private IUserResourcesService iUserResourcesService;
    
    /**
     * TODO(登入)    
     * @date: 2016年12月16日 上午9:43:16
     * @Title: loginCheck    
     * @param request
     * @return Object 返回值
     */
    @RequestMapping(value = "/loginCheck")
    @ResponseBody
    public Object loginCheck(HttpServletRequest request) {
        String userName = request.getParameter("uname");
        String userPwd = request.getParameter("upwd");
        ResultMsg rmg = iuserloginService.loginCheck(userName, userPwd);
        if (!rmg.isSuccess()) {
            return rmg;
        }
        request.getSession().setAttribute(CommonConstant.USER_INFO, rmg.getRutContent());
        //        登录验证完毕则交给shiro进行授权
        ShiroUtil.login(userName, Md5Encryption.MD5Encription(userPwd));
        return rmg;
    }
    
    /**
     * TODO(登出)    
     * @date: 2016年12月29日 下午4:56:42
     * @Title: signOut    
     * @param request void 返回值
     */
    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request) {
        if (null != request.getSession().getAttribute(CommonConstant.USER_INFO)) {
            logger.info(">> 用户注销 >> "
                + ((SysUserInfo) request.getSession().getAttribute(CommonConstant.USER_INFO)).getUserName());
            request.getSession().invalidate();
        }
        return null;
        //        登出以及跳转交给shiro了，见spring-shiro.xml
        //        ShiroUtil.logout();
        //        ModelAndView mv = new ModelAndView();
        //        mv.setViewName("login");
        //        return mv;
    }
    
    /**
     * 重置密码界面
     * @param request
     * @return
     */
    @RequestMapping(value = "/resetPwdPage")
    public ModelAndView resetPwdPage(HttpServletRequest request) {
        if (null != request.getSession().getAttribute(CommonConstant.USER_INFO)) {
            return new ModelAndView("views/common/resetPwd");
        }
        return new ModelAndView("login");
    }
    
    /**
     * 重置密码
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resetPwd")
    public Object resetPwd(HttpServletRequest request, ResetPwdDTO dto) {
        SysUserInfo currentUser = (SysUserInfo) request.getSession().getAttribute(CommonConstant.USER_INFO);
        dto.setUserId(currentUser.getUserId());
        return this.iuserloginService.resetPwd(dto);
    }
}
