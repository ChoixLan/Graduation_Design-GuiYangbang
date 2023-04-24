package com.gyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyu.Vo.Condition;
import com.gyu.Vo.PageResultVo;
import com.gyu.Vo.ResultVo;
import com.gyu.domain.Announcement;
import com.gyu.domain.UToU;
import com.gyu.dao.UToUDao;
import com.gyu.domain.Upkeep;
import com.gyu.service.UToUService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 快递代领 服务实现类
 * </p>
 *
 * @author choi
 * @since 2023-03-20
 */
@Service
public class UToUServiceImpl extends ServiceImpl<UToUDao, UToU> implements UToUService {

    @Autowired
    private UToUDao uToUDao;

    /**
     * 后台分页查询
     */
    @Override
    public ResultVo getPage(Condition condition) {
        Integer Current = Integer.parseInt(condition.getPageCurrent());
        Integer Size = Integer.parseInt(condition.getPageSize());

        //分页参数
        IPage page = new Page(Current,Size);

        uToUDao.selectPage(page, null);

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
    public ResultVo querySearch(Condition condition) {
        Integer Current = Integer.parseInt(condition.getPageCurrent());
        Integer Size = Integer.parseInt(condition.getPageSize());

        //分页参数
        IPage page = new Page(Current,Size);

        //queryWrapper组装查询where条件
        QueryWrapper<UToU> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("author_a", condition.getQueryString())
                .or()
                .like("address_q", condition.getQueryString())
                .or()
                .like("address_s", condition.getQueryString())
                .or()
                .like("author_b", condition.getQueryString());

        uToUDao.selectPage(page, queryWrapper);

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
    public ResultVo update(UToU uToU) {
        Integer code;
        String msg;
        int flag = uToUDao.updateById(uToU);
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
     * 小程序获取公告快递代领列表
     * @return
     */
    @Override
    public ResultVo getUToU() {
        Integer code;
        String msg;
        List<UToU> uToUList = uToUDao.selectList(null);
        if (!Objects.isNull(uToUList)) {
            code = 200;
            msg = "查询成功";
        } else {
            code = 301;
            msg = "查询失败";
        }
        ResultVo resultVo = new ResultVo(code, msg, uToUList);
        return resultVo;
    }

    /**
     * 小程序用户接取快递代领悬赏
     */
    @Override
    public ResultVo ReceivingUToU(UToU uToU) {
        Integer code;
        String msg;

        uToU.setStatusB(true);

        //queryWrapper组装查询where条件
        LambdaQueryWrapper<UToU> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UToU::getId, uToU.getId());

        int flag = uToUDao.update(uToU, queryWrapper);
        if (flag == 1) {
            code = 200;
            msg = "领取成功";
        } else {
            code = 301;
            msg = "领取失败";
        }
        ResultVo resultVo = new ResultVo(code, msg);
        return resultVo;
    }

    /**
     * 根据openid获取小程序用户的快递代领悬赏
     */
    @Override
    public ResultVo getMyUToU(UToU uToU) {
        Integer code;
        String msg;
        //queryWrapper组装查询where条件
        LambdaQueryWrapper<UToU> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UToU::getOpenId, uToU.getOpenId());
        List<UToU> uToUList = uToUDao.selectList(queryWrapper);
        if (!Objects.isNull(uToUList)) {
            code = 200;
            msg = "查询成功";
        } else {
            code = 301;
            msg = "查询失败";
        }
        ResultVo resultVo = new ResultVo(code, msg, uToUList);
        return resultVo;
    }

    /**
     * 获取小程序用户接取的快递代领悬赏
     */
    @Override
    public ResultVo getMyQUToU(UToU uToU) {
        Integer code;
        String msg;
        //queryWrapper组装查询where条件
        LambdaQueryWrapper<UToU> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UToU::getAuthorB, uToU.getAuthorB());
        List<UToU> uToUList = uToUDao.selectList(queryWrapper);
        if (!Objects.isNull(uToUList)) {
            code = 200;
            msg = "查询成功";
        } else {
            code = 301;
            msg = "查询失败";
        }
        ResultVo resultVo = new ResultVo(code, msg, uToUList);
        return resultVo;
    }

    /**
     *  插入新的悬赏
     */
    @Override
    public ResultVo insertUToU(UToU uToU) {
        Integer code;
        String msg;
        int flag = uToUDao.insert(uToU);
        if (flag == 1) {
            code = 200;
            msg = "插入成功";
        } else {
            code = 301;
            msg = "插入失败";
        }
        ResultVo resultVo = new ResultVo(code, msg);
        return resultVo;
    }

    /**
     *  小程序用户确认悬赏订单完成
     */
    @Override
    public ResultVo OkMyUToU(UToU uToU) {
        Integer code;
        String msg;
        uToU.setStatusA(true);
        int flag = uToUDao.updateById(uToU);
        if (flag == 1) {
            code = 200;
            msg = "确认成功";
        } else {
            code = 301;
            msg = "确认失败";
        }
        ResultVo resultVo = new ResultVo(code, msg);
        return resultVo;
    }

    /**
     *  小程序用户确认取消悬赏订单
     */
    @Override
    public ResultVo qMyUToU(UToU uToU) {
        Integer code;
        String msg;
        uToU.setStatusAx(true);
        int flag = uToUDao.updateById(uToU);
        if (flag == 1) {
            code = 200;
            msg = "取消成功";
        } else {
            code = 301;
            msg = "取消失败";
        }
        ResultVo resultVo = new ResultVo(code, msg);
        return resultVo;
    }


}
