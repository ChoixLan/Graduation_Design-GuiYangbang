package com.gyu.service;

import com.gyu.Vo.ResultVo;
import com.gyu.domain.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author choi
 * @since 2023-04-12
 */
public interface CommentService extends IService<Comment> {

    ResultVo addComment(Comment comment);

    ResultVo getComment(Comment comment);

    ResultVo addComment0(Comment comment);

    ResultVo getComment0(Comment comment);
}
