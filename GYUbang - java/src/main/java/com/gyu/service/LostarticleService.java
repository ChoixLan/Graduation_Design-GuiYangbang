package com.gyu.service;

import com.gyu.Vo.Condition;
import com.gyu.Vo.ResultVo;
import com.gyu.domain.Lostarticle;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 失物招领 服务类
 * </p>
 *
 * @author choi
 * @since 2023-04-06
 */
public interface LostarticleService extends IService<Lostarticle> {

    ResultVo getLostArticle();

    ResultVo insertLostA1(Lostarticle lostarticle);

    ResultVo insertLostA2(Lostarticle lostarticle);

    ResultVo getOneLostArticle(Lostarticle lostarticle);

    ResultVo getPage(Condition condition);

    ResultVo update(Lostarticle lostarticle);

    ResultVo querySearch(Condition condition);
}
