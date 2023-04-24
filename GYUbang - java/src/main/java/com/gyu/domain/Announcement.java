package com.gyu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 公告实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("announcement")
public class Announcement implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 发布者
     */
    private String author;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 楼栋
     */
    private String buildingNum;

    /**
     * 发布时间
     */
    private String releaseTime;

    /**
     * 是否启用
     */
    private boolean status;

}
