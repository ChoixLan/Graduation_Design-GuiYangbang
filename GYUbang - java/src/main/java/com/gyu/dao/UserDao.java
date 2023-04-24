package com.gyu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gyu.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author choi
 * @since 2023-03-14
 */
public interface UserDao extends BaseMapper<User> {

    @Select("select permission from sys_user where id = #{userid}")
    List<String> getPermissionByUserid(Integer userid);

    @Select("select Permission from sys_user where user_name = #{userName}")
    String getPermissionByName(String userName);

    @Insert("insert into sys_user(open_id) values (#{openid})")
    void insertOpenid(String openid);

    /**
     * 根据openid更新用户信息
     * @param user
     * @return
     */
    void updateByOpenId(User user);

    /**
     * 根据openid查询用户信息
     * @param openId
     * @return
     */
    @Select("select id from sys_user where open_id = #{openId}")
    String selectByOpenid(String openId);
}
