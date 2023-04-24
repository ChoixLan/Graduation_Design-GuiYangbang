package com.gyu.filter;

import com.gyu.domain.LoginUser;
import com.gyu.domain.User;
import com.gyu.utils.RedisCache;
import com.gyu.utils.TokenUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 定义token认证过滤器
 */
@Component
@Log4j2
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 如果请求头没有token，则对请求放行
            log.info("没有token");
            filterChain.doFilter(request,response);
            return;
        }

        // 解析token,获取userid
        String userid;
        try {
            TokenUtils.verifyToken(token);
            userid = tokenUtils.getUserIdByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }

        // 从redis中获取用户信息
        String redisKey;
        redisKey = "login:" + userid;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);

        if (Objects.isNull(loginUser)) {
            //如果没有这个redisKey
            throw new RuntimeException("用户未登录！");
        }


        // 存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                //TODO 获取权限信息封装到    Authentication  中                    参数3:权限信息
                //已认证状态，把loginUser存入principal
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行
        filterChain.doFilter(request,response);
    }
}
