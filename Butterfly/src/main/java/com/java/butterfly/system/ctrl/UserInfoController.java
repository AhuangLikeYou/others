package com.java.butterfly.system.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.butterfly.common.util.HttpRequestUtil;
import com.java.butterfly.system.dto.UserInfoDTO;
import com.java.butterfly.system.entity.SysUserInfo;
import com.java.butterfly.system.service.IUserInfoService;

/**
 * 用户信息维护
 */
@Controller
@RequestMapping(value = "/system/userInfo")
public class UserInfoController {
    private static Logger logger = Logger.getLogger(UserInfoController.class);
    
    @Resource
    private IUserInfoService userInfoService;
    
    /**
     * 用户管理界面
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userInfoPage")
    public ModelAndView userInfoPage(HttpServletRequest request) throws Exception {
        return new ModelAndView("views/system/userInfo");
    }
    
    /**
     * 用户管理界面列表查询
     * @param request
     * @param userinfoDTO
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getUsersList")
    public Object getUsersList(HttpServletRequest request, UserInfoDTO userinfoDTO) throws Exception {
        HttpRequestUtil.showRequestParameters(request);
        return userInfoService.queryListByPage(userinfoDTO);
    }
    
    /**
     * 用户管理-添加界面
     * @param request
     * @param userinfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addUserPage")
    public ModelAndView addUserPage(HttpServletRequest request, SysUserInfo userinfo) throws Exception {
        return new ModelAndView("views/system/userInfoAdd");
    }
    
    /**
     * 添加用户
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/addUser")
    public Object addUser(HttpServletRequest request, SysUserInfo userInfo) throws Exception {
        return userInfoService.addUser(userInfo);
    }
    
    /**
     * 删除用户
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/deleteUsers")
    public Object deleteUsers(HttpServletRequest request) throws Exception {
        return userInfoService.deleteUsers(request.getParameter("usersId"));
    }
    
    /**
     * 修改状态
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/updateStatus")
    public Object updateStatus(HttpServletRequest request, String userId) throws Exception {
        return userInfoService.updateStatus(userId);
    }
    
    /**
     * 用户详情
     * @param request
     * @param userinfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userInfoDetail")
    public ModelAndView userInfoDetail(HttpServletRequest request, String userId) throws Exception {
        SysUserInfo userinfo = this.userInfoService.getByUserId(userId);
        request.setAttribute("userinfo", userinfo);
        return new ModelAndView("views/system/userInfoDetail");
    }
    
    /**
     * 编辑用户信息
     * @param request
     * @param userInfo
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/updateDetail")
    public Object updateDetail(HttpServletRequest request, SysUserInfo userInfo) throws Exception {
        return userInfoService.updateUser(userInfo);
    }
    
    /**
     * 重置密码
     * @param request
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/resetPwd")
    public Object resetPwd(HttpServletRequest request, String userId) throws Exception {
        return userInfoService.resetPwd(userId);
    }
    
}
