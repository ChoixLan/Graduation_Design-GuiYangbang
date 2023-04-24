package com.gyu.utils;

import com.gyu.dao.UserDao;
import com.gyu.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {
    @Value("${JWT.tokenKey}")
    private String tokenKey;

    @Value("${JWT.dayMs}")
    private int dayMs;

    @Autowired
    private UserDao userDao;

    /**
     * 传入用户id，生成token
     */
    public String getToken(String  userid) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("userid", userid);
        map.put("created", new Date());
        return Jwts.builder()
                .setClaims(map)//放入paylode
                .signWith(SignatureAlgorithm.HS512, tokenKey)//放入token
                .setExpiration(new Date(System.currentTimeMillis() + dayMs * 7))//7天过期
                .compact();
    }

    /**
     * 验证token
     * @param token
     */
    public static void verifyToken(String token) {
            Jwts.parser().setSigningKey("dsasadQ@WE").parseClaimsJws(token);
    }

    /**
     * 验证token
     * 验证通过后，获取荷载信息
     * @param token
     */
    public Claims getPayloadByToken(String token) {
        try {
             return Jwts.parser()
                    .setSigningKey(tokenKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            return null;
        }
    }

    /**
     * 根据token得到用户id
     * @param token
     */
    public String getUserIdByToken(String token) {
        return (String) this.getPayloadByToken(token).get("userid");
    }

    /**
     * 根据token得到用户信息
     * @param token
     * @return
     */
    public User getUserByToken(String token) {
        String userid = this.getUserIdByToken(token);
        User user = userDao.selectById(userid);
        return user;
    }

    /**
     * 根据token判断当前时间内，该token是否过期
     * @param token
     */
    public boolean isExpiration(String token) {
        return this.getPayloadByToken(token).getExpiration().before(new Date());
    }

}
