package com.gyu.service;

import com.gyu.Vo.Condition;
import com.gyu.Vo.ResultVo;
import com.gyu.domain.UToU;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 快递代领 服务类
 * </p>
 *
 * @author choi
 * @since 2023-03-20
 */
public interface UToUService extends IService<UToU> {

    public ResultVo getPage(Condition condition);

    public ResultVo querySearch(Condition condition);

    public ResultVo update(UToU uToU);

    ResultVo getUToU();

    ResultVo ReceivingUToU(UToU uToU);

    ResultVo getMyUToU(UToU uToU);

    ResultVo OkMyUToU(UToU uToU);

    ResultVo qMyUToU(UToU uToU);

    ResultVo getMyQUToU(UToU uToU);

    ResultVo insertUToU(UToU uToU);
}
