package com.gyu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gyu.Vo.Condition;
import com.gyu.domain.Announcement;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AnnouncementDao extends BaseMapper<Announcement> {
}
