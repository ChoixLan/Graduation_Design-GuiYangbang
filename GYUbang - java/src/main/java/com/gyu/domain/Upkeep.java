package com.gyu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 维修
 * </p>
 *
 * @author choi
 * @since 2023-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Upkeep implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;

    /**
     * 维修原因
     */
    private String cause;

    /**
     * 维修地址
     */
    private String address;

    /**
     * 申请人
     */
    private String author;

    /**
     * open_id
     */
    private String openId;

    /**
     * 申请日期
     */
    private String applicationTime;

    /**
     * 是否处理
     */
    private Boolean statusIf;

    /**
     * 处理结果
     */
    private Boolean status;

    /**
     * 处理反馈
     */
    private String statusFeedback;

    /**
     * 处理时间
     */
    private String statusTime;

}
