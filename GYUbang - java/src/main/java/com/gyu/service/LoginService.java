package com.gyu.service;

import com.gyu.Vo.ResultVo;
import com.gyu.domain.User;

public interface LoginService {

    /**
     * 登录接口
     * @param user
     * @return 返回token，用token获取资源
     */
    ResultVo login(User user);

    /**
     * 小程序登录接口
     * @param openid
     * @return
     */
    ResultVo miniLogin(String openid);

    /**
     * 退出接口
     * @return
     */
    ResultVo logout();
}
