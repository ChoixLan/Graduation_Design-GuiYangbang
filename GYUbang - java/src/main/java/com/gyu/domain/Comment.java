package com.gyu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 评论
 * </p>
 *
 * @author choi
 * @since 2023-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;

    /**
     * 头像
     */
    private String image;

    /**
     * 昵称
     */
    @TableField("nickName")
    private String nickname;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private String time;

    /**
     * 类型
     */
    private Boolean status;

    /**
     * 失物ID
     */
    @TableField("lostId")
    private Integer lostid;


}
