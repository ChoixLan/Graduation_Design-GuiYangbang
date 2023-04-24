package com.gyu;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyu.dao.UserDao;
import com.gyu.domain.User;
import com.gyu.utils.TokenUtils;
import com.gyu.utils.VerificationCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private VerificationCode verificationCode;

    @Test
    public void  test99() {
        User user = new User();
        user.setNickName("choi");
        user.setOpenId("oo4EN5bsCcOWH3QjZH3wOVdLzv0g");
        userDao.updateByOpenId(user);
    }

    @Test
    public void test0() {
        //分页参数
        IPage page = new Page(1,2);

        //queryWrapper组装查询where条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPermission,"user");
        userDao.selectPage(page, null);
        System.out.println("当前页码：" + page.getCurrent());
        System.out.println("每页显示数" + page.getSize());
        System.out.println("一共多少页" + page.getPages());
        System.out.println("一共多少条数据" + page.getTotal());
        System.out.println("数据" + page.getRecords());
    }

    @Test
    public void TestBCryptPasswordEncoder() {
        //加密明文密码
        String encode = passwordEncoder.encode("123");
        System.out.println(encode);
        //1234 $2a$10$ZNhtPwIIQlXpbROYf0oXIeGm/hN/dfIhYXKlRDVOPTzdxTRRdNtO6
        //123  $2a$10$6t5k2KM15wdWVQc1.vtVT.0hTKqTvOFORBP5KFsgGqZPk3S1g4Fz.
        //用户输入的明文密码和数据库加密密码比较
        boolean matches = passwordEncoder.matches("1234",encode);
        System.out.println(matches);
    }


}
