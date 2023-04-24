package com.gyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyu.Vo.PageResultVo;
import com.gyu.Vo.ResultVo;
import com.gyu.Vo.Condition;
import com.gyu.dao.UserDao;
import com.gyu.domain.User;
import com.gyu.service.UserService;
import com.gyu.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author choi
 * @since 2023-03-14
 */
@Service
@Log4j2
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResultVo getPageUserByClassification(Condition condition) {
        Integer Current = Integer.parseInt(condition.getPageCurrent());
        Integer Size = Integer.parseInt(condition.getPageSize());

        //分页参数
        IPage page = new Page(Current,Size);

        //queryWrapper组装查询where条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        switch (condition.getClassification()) {
            case "1":  queryWrapper.like(User::getCollege, condition.getQueryString());break;
            case "2":  queryWrapper.like(User::getNickName, condition.getQueryString());break;
            case "3":  queryWrapper.like(User::getBuildingNum, condition.getQueryString());break;
            case "4":  queryWrapper.like(User::getPermission, condition.getQueryString());break;
        }

        userDao.selectPage(page, queryWrapper);

        PageResultVo pageResultVo = new PageResultVo();
        pageResultVo.setCurrent(page.getCurrent());
        pageResultVo.setSize(page.getSize());
        pageResultVo.setPages(page.getPages());
        pageResultVo.setTotal(page.getTotal());
        pageResultVo.setRecords(page.getRecords());

        Integer code;
        String msg;
        if (Objects.isNull(page.getRecords())) {
            code = 301;
            msg = "查询失败";
        } else {
            code = 200;
            msg = "查询成功";
        }
        ResultVo resultVo = new ResultVo(code,msg,pageResultVo);

        return resultVo;
    }

    @Override
    public ResultVo getPageUser(Condition condition) {
        Integer Current = Integer.parseInt(condition.getPageCurrent());
        Integer Size = Integer.parseInt(condition.getPageSize());

        //分页参数
        IPage page = new Page(Current,Size);

        userDao.selectPage(page, null);

        PageResultVo pageResultVo = new PageResultVo();
        pageResultVo.setCurrent(page.getCurrent());
        pageResultVo.setSize(page.getSize());
        pageResultVo.setPages(page.getPages());
        pageResultVo.setTotal(page.getTotal());
        pageResultVo.setRecords(page.getRecords());

        Integer code;
        String msg;
        if (Objects.isNull(page.getRecords())) {
            code = 301;
            msg = "查询失败";
        } else {
            code = 200;
            msg = "查询成功";
        }
        ResultVo resultVo = new ResultVo(code,msg,pageResultVo);

        return resultVo;
    }

    @Override
    public ResultVo deleteUserById(int id) {
        Integer code;
        String msg;
        int flag = userDao.deleteById(id);
        if (flag == 1) {
            code = 200;
            msg = "删除成功";
        } else {
            code = 301;
            msg = "删除失败";
        }

        ResultVo resultVo = new ResultVo(code, msg);

        return resultVo;
    }

    @Override
    public ResultVo insert(User user) {
        Integer code;
        String msg;

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        int flag = userDao.insert(user);
        if (flag == 1) {
            code = 200;
            msg = "添加成功";
        } else {
            code = 301;
            msg = "添加失败";
        }
        ResultVo resultVo = new ResultVo(code, msg);
        return resultVo;
    }

    @Override
    public ResultVo update(User user) {
        Integer code;
        String msg;
        int flag = userDao.updateById(user);
        if (flag == 1) {
            code = 200;
            msg = "修改成功";
        } else {
            code = 301;
            msg = "修改失败";
        }
        ResultVo resultVo = new ResultVo(code, msg);
        return resultVo;
    }

    /**
     * 根据openid更新用户信息
     * @param user
     */
    @Override
    public ResultVo updateByOpenId(User user) {
        // 判断openid是否为空
        if (StringUtils.isEmpty(user.getOpenId())) {
            return ResultVo.fail("请传递小程序唯一标识openid");
        }
        userDao.updateByOpenId(user);
        return ResultVo.success("用户信息更新成功");
    }

    @Override
    public ResultVo getUserByOpenid(String openId) {
        Integer code;
        String msg;
        String id = userDao.selectByOpenid(openId);
        User user = userDao.selectById(id);
        log.info(user);
        if (!Objects.isNull(user)) {
            code = 200;
            msg = "查询成功";
        } else {
            code = 301;
            msg = "查询失败";
        }
        ResultVo resultVo = new ResultVo(code, msg, user);
        return resultVo;
    }

}
