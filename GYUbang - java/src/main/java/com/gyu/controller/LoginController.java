package com.gyu.controller;

import com.gyu.Vo.ResultVo;
import com.gyu.domain.User;
import com.gyu.service.LoginService;
import com.gyu.utils.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 自定义登录接口
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    public VerificationCode verificationCode;

    @RequestMapping("/getCode")
    public ResultVo getCode() {
        return verificationCode.makeNum();
    }

    @PostMapping("/login")
    public ResultVo login(@RequestBody User user) {
        // 登录
        return loginService.login(user);
    }

    @RequestMapping("/logout")
    public ResultVo logout() {
        // 退出
        return loginService.logout();
    }
}
