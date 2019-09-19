package com.java.butterfly.system.service;

import com.java.butterfly.common.dto.ResultMsg;
import com.java.butterfly.system.dto.ResetPwdDTO;

/**
 * 用户登录     
 * @ClassName: IUserLoginService    
 * @author xulu    
 * @date: 2017年4月29日 下午2:32:45    
 * @version  v 1.0
 */
public interface IUserLoginService {
    /**
     * login method    
     * @author xulu    
     * @date: 2017年4月29日 下午2:32:11
     * @Title: loginCheck    
     * @param userName
     * @param userPwd
     * @return ResultMsg 返回值
     */
    public ResultMsg loginCheck(String userName, String userPwd);
    
    /**
     * 修改密码
     * @param dto
     * @return
     */
    public ResultMsg resetPwd(ResetPwdDTO dto);
}
