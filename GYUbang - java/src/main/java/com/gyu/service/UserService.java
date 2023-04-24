package com.gyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gyu.Vo.ResultVo;
import com.gyu.Vo.Condition;
import com.gyu.domain.Upkeep;
import com.gyu.domain.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author choi
 * @since 2023-03-14
 */
public interface UserService extends IService<User> {

    public ResultVo getPageUserByClassification(Condition condition);

    ResultVo getPageUser(Condition condition);

    ResultVo deleteUserById(int id);

    ResultVo insert(User user);

    ResultVo update(User user);

    /**
     * 根据openid更新用户信息
     * @param user
     */
    ResultVo updateByOpenId(User user);

    ResultVo getUserByOpenid(String openId);

}
