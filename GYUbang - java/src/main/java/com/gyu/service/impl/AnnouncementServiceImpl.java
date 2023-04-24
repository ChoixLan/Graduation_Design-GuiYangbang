package com.gyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyu.Vo.Condition;
import com.gyu.Vo.PageResultVo;
import com.gyu.Vo.ResultVo;
import com.gyu.dao.AnnouncementDao;
import com.gyu.domain.Announcement;
import com.gyu.domain.User;
import com.gyu.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementDao, Announcement> implements AnnouncementService {

    @Autowired
    private AnnouncementDao announcementDao;

    @Override
    public ResultVo getPage(Condition condition) {
        Integer Current = Integer.parseInt(condition.getPageCurrent());
        Integer Size = Integer.parseInt(condition.getPageSize());

        //分页参数
        IPage page = new Page(Current,Size);

        announcementDao.selectPage(page, null);

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
    public ResultVo update(Announcement announcement) {
        Integer code;
        String msg;
        int flag = announcementDao.updateById(announcement);
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
     * 后台公告搜索
     */
    @Override
    public ResultVo querySearch(Condition condition) {
        Integer Current = Integer.parseInt(condition.getPageCurrent());
        Integer Size = Integer.parseInt(condition.getPageSize());

        //分页参数
        IPage page = new Page(Current,Size);

        //queryWrapper组装查询where条件
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", condition.getQueryString())
                .or()
                .like("author", condition.getQueryString())
                .or()
                .like("content", condition.getQueryString());

        announcementDao.selectPage(page, queryWrapper);

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

    /**
     * 小程序公告搜索
     * @param condition
     * @return
     */
    @Override
    public ResultVo querySearchMini(Condition condition) {
        //queryWrapper组装查询where条件
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", condition.getQueryString())
                .or()
                .like("author", condition.getQueryString())
                .or()
                .like("content", condition.getQueryString());

        List<Announcement> announcements = announcementDao.selectList(queryWrapper);
        Integer code;
        String msg;
        if (Objects.isNull(announcements)) {
            code = 301;
            msg = "查询失败";
        } else {
            code = 200;
            msg = "查询成功1";
        }
        ResultVo resultVo = new ResultVo(code,msg,announcements);

        return resultVo;
    }

    /**
     * 根据id获取公告
     * @param announcement
     * @return
     */
    @Override
    public ResultVo getAnnouncementById(Announcement announcement) {
        Integer code;
        String msg;

        Announcement announcement1 = announcementDao.selectById(announcement.getId());

        if (!Objects.isNull(announcement1)) {
            code = 200;
            msg = "查询成功";
        } else {
            code = 301;
            msg = "查询失败";
        }
        ResultVo resultVo = new ResultVo(code, msg, announcement1);
        return resultVo;
    }

    /**
     * 添加
     */
    @Override
    public ResultVo insert(Announcement announcement) {
        Integer code;
        String msg;
        announcement.setStatus(true);
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        announcement.setReleaseTime(dateFormat.format(date));

        int flag = announcementDao.insert(announcement);
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
    public ResultVo getAnnouncement() {
        Integer code;
        String msg;

        List<Announcement> announcements = announcementDao.selectList(null);
        if (!Objects.isNull(announcements)) {
            code = 200;
            msg = "查询成功";
        } else {
            code = 301;
            msg = "查询失败";
        }
        ResultVo resultVo = new ResultVo(code, msg, announcements);
        return resultVo;
    }
}
