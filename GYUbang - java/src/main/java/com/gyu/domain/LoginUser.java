package com.gyu.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * UserDetails对象，用于自定义接收登录函数接收查询到的用户信息，返回调用者
 */
@Data
@NoArgsConstructor//空参构造
//@AllArgsConstructor//全参构造
public class LoginUser implements UserDetails {
    private User user;

    // 权限信息
    private List<String> permissions;

    // authorities成员变量不被redis序列化
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public LoginUser(User user) {
        this.user = user;
    }

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    /**
     * 返回权限信息
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        /**
         * 如果之前调用过getAuthorities方法获取权限信息，直接返回之前存入的权限信息
         * 优化程序执行速度，不需要每次调用都转换
         */
        if (authorities != null) {
            return authorities;
        }

        // 把permissions中的String类型的权限封装成SimpleGrantedAuthority对象

//        方式一
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            if (permission != null) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
                authorities.add(authority);
            }
        }

        // 方式二
//        authorities = permissions.stream()
//                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
