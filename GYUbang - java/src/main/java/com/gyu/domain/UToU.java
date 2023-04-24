package com.gyu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 快递代领
 * </p>
 *
 * @author choi
 * @since 2023-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UToU implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发布人
     */
    private String authorA;

    /**
     * open_id
     */
    private String openId;

    /**
     * 取件地址
     */
    private String addressQ;

    /**
     * 取件密码
     */
    private String password;

    /**
     * 送件地址
     */
    private String addressS;

    /**
     * 悬赏金额
     */
    private String money;

    /**
     * 是否取消
     */
    private Boolean statusAx;

    /**
     * 是否完成
     */
    private Boolean statusA;

    /**
     * 是否接取
     */
    private Boolean statusB;

    /**
     * 接取人
     */
    private String authorB;

    /**
     * 管理员冻结
     */
    private Boolean statusC;

    /**
     * 冻结反馈
     */
    private String statusFeedback;


}
