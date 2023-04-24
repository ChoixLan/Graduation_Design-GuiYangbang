package com.gyu.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gyu.Vo.ResultVo;
import com.gyu.domain.Upkeep;
import com.gyu.domain.User;
import com.gyu.service.LoginService;
import com.gyu.service.UpkeepService;
import com.gyu.service.UserService;
import com.gyu.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpUtils;
import java.io.IOException;

/**
 * 小程序业务控制层
 */
@Log4j2
@RestController
@RequestMapping("/mini")
public class MiniController {

    @Value("${mini.appid}")
    private String appid;

    @Value("${mini.secret}")
    private String secret;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private UpkeepService upkeepService;

    /**
     * 小程序用户登录系统
     */
    @GetMapping("/login")
    public ResultVo login(String code) throws IOException {
        if (StringUtils.isEmpty(code)) {
            return ResultVo.fail("登录失败, 请联系管理员！");
        }
        // 创建一个Client请求
        CloseableHttpClient client = HttpClientBuilder.create().build();
        // 构建get请求
        String url = "https://api.weixin.qq.com/sns/jscode2session?" + "appid=" +
                appid +
                "&secret=" +
                secret +
                "&js_code=" +
                code +
                "&grant_type=authorization_code";
        HttpGet httpGet = new HttpGet(url);
        // 发起请求，获取openid和session_key
        CloseableHttpResponse response = client.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject = JSON.parseObject(result);
        log.info("jsonObject-->{}", jsonObject);
        String openid = jsonObject.getString("openid");
        log.info("openid-->{}", openid);

        return loginService.miniLogin(openid);
    }

    /**
     * 根据openid获取用户信息
     */
    @PostMapping("/getUserInfo")
    public ResultVo getUserInfo(@RequestBody User user) {
        String openId = user.getOpenId();
        log.info(openId);
        return userService.getUserByOpenid(openId);
    }

    /**
     * 根据openid更新数据库用户信息
     */
    @PostMapping("/update/info")
    public ResultVo updateInfo(@RequestBody User user) {
        log.info(user);
        return userService.updateByOpenId(user);
    }





}
