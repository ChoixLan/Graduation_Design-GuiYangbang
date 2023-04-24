package com.gyu.service;

import com.gyu.Vo.Condition;
import com.gyu.Vo.ResultVo;
import com.gyu.domain.Upkeep;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gyu.domain.User;

/**
 * <p>
 * 维修 服务类
 * </p>
 *
 * @author choi
 * @since 2023-03-19
 */
public interface UpkeepService extends IService<Upkeep> {

    public ResultVo getPage(Condition condition);

    public ResultVo querySearch(Condition condition);

    public ResultVo update(Upkeep upkeep);

    ResultVo getUpkeepInfo(User user);

    ResultVo insertUpkeep(Upkeep upkeep);
}
