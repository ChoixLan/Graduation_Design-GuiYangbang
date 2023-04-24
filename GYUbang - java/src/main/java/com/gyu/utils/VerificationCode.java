package com.gyu.utils;

import com.gyu.Vo.ResultVo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 生成验证码
 */
@Component
public class VerificationCode {

    //生成随机数
    public ResultVo makeNum() {
        Random random = new Random();
        //加一个""可以将int转为String，相当于强转
        String num = random.nextInt(9999999) + "";
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 7 - num.length(); i++) {
            //不足七位用0填充
            stringBuffer.append(0);
        }
        num = stringBuffer.toString() + num;

        Integer code = 200;
        String msg = "验证码获取成功";

        Map<String, String> map = new HashMap<>();
        map.put("VerificationCode",num);

        ResultVo resultVo = new ResultVo(code, msg, map);
        return resultVo;
    }
}
