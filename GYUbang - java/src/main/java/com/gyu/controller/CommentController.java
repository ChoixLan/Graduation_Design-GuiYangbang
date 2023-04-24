package com.gyu.controller;


import com.gyu.Vo.ResultVo;
import com.gyu.domain.Comment;
import com.gyu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author choi
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加失物评论
     */
    @PostMapping("/addComment")
    public ResultVo addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    /**
     * 添加招领评论
     */
    @PostMapping("/addComment0")
    public ResultVo addComment0(@RequestBody Comment comment) {
        return commentService.addComment0(comment);
    }

    /**
     * 获取失物评论
     */
    @PostMapping("/getComment")
    public ResultVo getComment(@RequestBody Comment comment) {
        return commentService.getComment(comment);
    }

    /**
     * 获取招领评论
     */
    @PostMapping("/getComment0")
    public ResultVo getComment0(@RequestBody Comment comment) {
        return commentService.getComment0(comment);
    }

}

