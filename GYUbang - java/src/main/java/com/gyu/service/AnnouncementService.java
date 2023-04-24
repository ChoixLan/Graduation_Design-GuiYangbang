package com.gyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gyu.Vo.Condition;
import com.gyu.Vo.ResultVo;
import com.gyu.dao.AnnouncementDao;
import com.gyu.domain.Announcement;

public interface AnnouncementService extends IService<Announcement> {
    public ResultVo getPage(Condition condition);

    public ResultVo update(Announcement announcement);

    public ResultVo querySearch(Condition condition);

    public ResultVo insert(Announcement announcement);

    ResultVo getAnnouncement();

    ResultVo querySearchMini(Condition condition);

    ResultVo getAnnouncementById(Announcement announcement);
}
