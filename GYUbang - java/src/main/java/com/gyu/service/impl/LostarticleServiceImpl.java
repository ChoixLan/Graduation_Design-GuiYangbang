package com.gyu.service.impl;

import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyu.Vo.Condition;
import com.gyu.Vo.PageResultVo;
import com.gyu.Vo.ResultVo;
import com.gyu.domain.Announcement;
import com.gyu.domain.Lostarticle;
import com.gyu.dao.LostarticleDao;
import com.gyu.service.LostarticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 失物招领 服务实现类
 * </p>
 *
 * @author choi
 * @since 2023-04-06
 */
@Service
@Log4j2
public class LostarticleServiceImpl extends ServiceImpl<LostarticleDao, Lostarticle> implements LostarticleService {

    @Autowired
    private LostarticleDao lostarticleDao;

    /**
     * 小程序获取失物招领信息
     * @return
     */
    @Override
    public ResultVo getLostArticle() {
        Integer code;
        String msg;

        List<Lostarticle> lostArticle = lostarticleDao.selectList(null);
        if (!Objects.isNull(lostArticle)) {
            code = 200;
            msg = "查询成功";
        } else {
            code = 301;
            msg = "查询失败";
        }
        ResultVo resultVo = new ResultVo(code, msg, lostArticle);
        return resultVo;
    }

    /**
     * 小程序插入失物信息
     */
    @Override
    public ResultVo insertLostA1(Lostarticle lostarticle) {
        Integer code;
        String msg;
        lostarticle.setStatus(false);

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        lostarticle.setTime(dateFormat.format(date));

        int flag = lostarticleDao.insert(lostarticle);
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

    /**
     * 小程序插入招领信息
     */
    @Override
    public ResultVo insertLostA2(Lostarticle lostarticle) {
        Integer code;
        String msg;
        lostarticle.setStatus(true);

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        lostarticle.setTime(dateFormat.format(date));

        int flag = lostarticleDao.insert(lostarticle);
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

    /**
     *  小程序根据id获取失物招领信息
     */
    @Override
    public ResultVo getOneLostArticle(Lostarticle lostarticle) {
        Integer code;
        String msg;
        Lostarticle lostarticle1 = lostarticleDao.selectById(lostarticle.getId());
        if (!Objects.isNull(lostarticle1)) {
            code = 200;
            msg = "查询成功";
        } else {
            code = 301;
            msg = "查询失败";
        }
        ResultVo resultVo = new ResultVo(code, msg, lostarticle1);
        return resultVo;
    }

    /**
     * 后台分页查询
     */
    @Override
    public ResultVo getPage(Condition condition) {
        Integer Current = Integer.parseInt(condition.getPageCurrent());
        Integer Size = Integer.parseInt(condition.getPageSize());

        //分页参数
        IPage page = new Page(Current,Size);

        lostarticleDao.selectPage(page, null);

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
     * 后台更新失物招领
     */
    @Override
    public ResultVo update(Lostarticle lostarticle) {

        Integer code;
        String msg;
        int flag = lostarticleDao.updateById(lostarticle);
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
     * 后台模糊搜索
     */
    @Override
    public ResultVo querySearch(Condition condition) {
        Integer Current = Integer.parseInt(condition.getPageCurrent());
        Integer Size = Integer.parseInt(condition.getPageSize());

        //分页参数
        IPage page = new Page(Current,Size);

        //queryWrapper组装查询where条件
        QueryWrapper<Lostarticle> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("author", condition.getQueryString())
                .or()
                .like("content", condition.getQueryString());

        lostarticleDao.selectPage(page, queryWrapper);

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
}
