package com.gyu.config.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gyu.dao.UserDao;
import com.gyu.domain.LoginUser;
import com.gyu.domain.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 重写loadUserByUsername
 * 根据authenticate()传递过来的username到数据库查询是否有这个用户名
 * 如果有就就将这个用户信息封装成LoginUser（自定义的接收用户数据信息的响应类）返回
 */
@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username)
                    .or()
                    .eq(User::getOpenId, username);
        User user = userDao.selectOne(queryWrapper);

        //没有查询到用户，则抛出异常
        if (Objects.isNull(user)) {
            //throw new RuntimeException("用户名或密码错误！！！");
            return null;
        }

        //查询权限信息
        List<String> list = userDao.getPermissionByUserid(user.getId());

        //把数据封装称UserDetails返回
        return new LoginUser(user, list);
    }
}
