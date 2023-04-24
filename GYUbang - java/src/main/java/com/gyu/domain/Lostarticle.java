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
 * 失物招领
 * </p>
 *
 * @author choi
 * @since 2023-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Lostarticle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布人
     */
    private String author;

    /**
     * 联系方式
     */
    private String tel;

    /**
     * 发布日期
     */
    private String time;

    /**
     * 图片
     */
    private String image;

    /**
     * 类型
     */
    private Boolean status;

    /**
     * 是否启用
     */
    @TableField("statusX")
    private Boolean statusx;


}
