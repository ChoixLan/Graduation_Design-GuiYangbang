package com.gyu.config.security.hadler;

import com.alibaba.fastjson.JSON;
import com.gyu.Vo.ResultVo;
import com.gyu.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常处理方法
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResultVo resultVO = new ResultVo(401, "用户认证失败，请检查后登录！");
        String jsonString = JSON.toJSONString(resultVO);
        //异常处理
        WebUtils.renderString(response, jsonString);
    }
}
