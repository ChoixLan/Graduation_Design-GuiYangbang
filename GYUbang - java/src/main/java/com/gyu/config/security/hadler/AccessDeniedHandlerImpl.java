package com.gyu.config.security.hadler;

import com.alibaba.fastjson.JSON;
import com.gyu.Vo.ResultVo;
import com.gyu.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权异常处理
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResultVo resultVO = new ResultVo(403, "用户授权失败，请查询后登录！");
        String jsonString = JSON.toJSONString(resultVO);
        //异常处理
        WebUtils.renderString(response, jsonString);
    }
}
