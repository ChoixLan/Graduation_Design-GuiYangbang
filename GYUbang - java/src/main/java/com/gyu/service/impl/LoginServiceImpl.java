package com.gyu.service.impl;

import com.gyu.config.security.impl.UserDetailsServiceImpl;
import com.gyu.dao.UserDao;
import com.gyu.domain.LoginUser;
import com.gyu.Vo.ResultVo;
import com.gyu.domain.User;
import com.gyu.service.LoginService;
import com.gyu.utils.RedisCache;
import com.gyu.utils.TokenUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 登录业务实现
 */
@Service
@Log4j2
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * 后台账号密码登录
     */
    @Override
    public ResultVo login(User user) {
        // AuthenticationManager.authenticate进行用户账号密码认证
        // 把用户名密码封装成UsernamePasswordAuthenticationToken对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        // authenticate()会调用自己重写的loadUserByUsername进行验证
        // 如果authentication结果 不为 NULL则认证通过
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 如果认证没有通过，返回提示信息
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("登录失败！！！");
        }

        // 如果认证通过，使用id生成一个jwt
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        log.info("userid: {}", id);
        String token = tokenUtils.getToken(id);

        // 把完整的用户信息存入redis id作为key
        redisCache.setCacheObject("login:" + id, loginUser);

        // 获取用户权限
        String permission = userDao.getPermissionByName(user.getUserName());

        // jwt存入响应类返回
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("permission", permission);

        return new ResultVo(200, "登录成功！！！", map);
    }

    /**
     * 后台退出登录
     */
    @Override
    public ResultVo logout() {
        // 获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer userid = loginUser.getUser().getId();

        // 删除redis中的值
        redisCache.deleteObject("login:" + userid);
        return new ResultVo(200, "注销成功！");
    }

    /**
     * 微信小程序登录
     */
    @Override
    public ResultVo miniLogin(String openid) {
        LoginUser loginUser;
        loginUser = (LoginUser) userDetailsService.loadUserByUsername(openid);
        if (loginUser == null) {
            userDao.insertOpenid(openid);
            loginUser = (LoginUser) userDetailsService.loadUserByUsername(openid);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 如果认证通过, 使用id生成一个jwt
        String id = loginUser.getUser().getId().toString();
        String token = tokenUtils.getToken(id);

        // 把完整的用户信息存入redis, id作为key
        redisCache.setCacheObject("login:" + id, loginUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername(openid);

        Map<String, Object> map = new HashMap<>(2);
        map.put("token", token);
        map.put("userInfo", userDetails);
        map.put("openid", openid);

        return new ResultVo(200, "登录成功！！！", map);
    }


}
