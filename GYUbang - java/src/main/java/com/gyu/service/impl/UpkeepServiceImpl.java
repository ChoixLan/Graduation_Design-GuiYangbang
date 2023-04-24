package com.gyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyu.Vo.Condition;
import com.gyu.Vo.PageResultVo;
import com.gyu.Vo.ResultVo;
import com.gyu.domain.Announcement;
import com.gyu.domain.Upkeep;
import com.gyu.dao.UpkeepDao;
import com.gyu.domain.User;
import com.gyu.service.UpkeepService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 维修 服务实现类
 * </p>
 *
 * @author choi
 * @since 2023-03-19
 */
@Service
public class UpkeepServiceImpl extends ServiceImpl<UpkeepDao, Upkeep> implements UpkeepService {

    @Autowired
    private UpkeepDao upkeepDao;

    /**
     * 后台获取维修申请数据
     * @param condition
     * @return
     */
    @Override
    public ResultVo getPage(Condition condition) {
        Integer Current = Integer.parseInt(condition.getPageCurrent());
        Integer Size = Integer.parseInt(condition.getPageSize());

        //分页参数
        IPage page = new Page(Current,Size);

        upkeepDao.selectPage(page, null);

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
     * 管理员后台搜索维修申请
     * @param condition
     * @return
     */
    @Override
    public ResultVo querySearch(Condition condition) {
        Integer Current = Integer.parseInt(condition.getPageCurrent());
        Integer Size = Integer.parseInt(condition.getPageSize());

        //分页参数
        IPage page = new Page(Current,Size);

        //queryWrapper组装查询where条件
        QueryWrapper<Upkeep> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("cause", condition.getQueryString())
                .or()
                .like("address", condition.getQueryString())
                .or()
                .like("author", condition.getQueryString());

        upkeepDao.selectPage(page, queryWrapper);

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
     * 管理员后台处理
     * @param upkeep
     * @return
     */
    @Override
    public ResultVo update(Upkeep upkeep) {

        Integer code;
        String msg;

        upkeep.setStatusIf(true);

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        upkeep.setStatusTime(dateFormat.format(date));

        int flag = upkeepDao.updateById(upkeep);
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
     * 根据openid获取我的维修申请
     */
    @Override
    public ResultVo getUpkeepInfo(User user) {
        Integer code;
        String msg;

        //queryWrapper组装查询where条件
        LambdaQueryWrapper<Upkeep> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Upkeep::getOpenId, user.getOpenId());

        List<Upkeep> upkeepList = upkeepDao.selectList(queryWrapper);
        if (!Objects.isNull(upkeepList)) {
            code = 200;
            msg = "查询成功";
        } else {
            code = 301;
            msg = "查询失败";
        }
        ResultVo resultVo = new ResultVo(code, msg, upkeepList);
        return resultVo;
    }

    /**
     * 小程序用户提交新的维修申请
     * @param upkeep
     * @return
     */
    @Override
    public ResultVo insertUpkeep(Upkeep upkeep) {
        Integer code;
        String msg;

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        upkeep.setApplicationTime(dateFormat.format(date));

        int flag = upkeepDao.insert(upkeep);
        if (flag == 1) {
            code = 200;
            msg = "申请插入成功";
        } else {
            code = 301;
            msg = "申请插入失败";
        }
        ResultVo resultVo = new ResultVo(code, msg);
        return resultVo;
    }


}
