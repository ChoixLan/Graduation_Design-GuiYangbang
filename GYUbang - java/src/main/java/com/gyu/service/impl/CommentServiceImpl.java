package com.gyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyu.Vo.ResultVo;
import com.gyu.domain.Announcement;
import com.gyu.domain.Comment;
import com.gyu.dao.CommentDao;
import com.gyu.domain.Upkeep;
import com.gyu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author choi
 * @since 2023-04-12
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Autowired
    private CommentDao commentDao;

    /**
     * 添加失物评论
     */
    @Override
    public ResultVo addComment(Comment comment) {
        Integer code;
        String msg;
        comment.setStatus(false);
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        comment.setTime(dateFormat.format(date));

        int flag = commentDao.insert(comment);
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
     * 添加招领评论
     */
    @Override
    public ResultVo addComment0(Comment comment) {
        Integer code;
        String msg;
        comment.setStatus(true);
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        comment.setTime(dateFormat.format(date));

        int flag = commentDao.insert(comment);
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
     * 获取失物评论
     */
    @Override
    public ResultVo getComment(Comment comment) {
        Integer code;
        String msg;

        //queryWrapper组装查询where条件
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getLostid, comment.getLostid())
                .and((Wrapper)-> {
                    Wrapper.eq(Comment::getStatus, false);
                });

        List<Comment> commentList = commentDao.selectList(queryWrapper);
        if (!Objects.isNull(commentList)) {
            code = 200;
            msg = "查询成功";
        } else {
            code = 301;
            msg = "查询失败";
        }
        ResultVo resultVo = new ResultVo(code, msg, commentList);
        return resultVo;
    }

    /**
     * 获取失物评论
     */
    @Override
    public ResultVo getComment0(Comment comment) {
        Integer code;
        String msg;

        //queryWrapper组装查询where条件
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getLostid, comment.getLostid())
                .and((Wrapper)-> {
                    Wrapper.eq(Comment::getStatus, true);
                });

        List<Comment> commentList = commentDao.selectList(queryWrapper);
        if (!Objects.isNull(commentList)) {
            code = 200;
            msg = "查询成功";
        } else {
            code = 301;
            msg = "查询失败";
        }
        ResultVo resultVo = new ResultVo(code, msg, commentList);
        return resultVo;
    }

}
