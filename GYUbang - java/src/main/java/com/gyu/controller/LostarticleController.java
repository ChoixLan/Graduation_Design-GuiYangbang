package com.gyu.controller;


import com.gyu.Vo.Condition;
import com.gyu.Vo.ResultVo;
import com.gyu.domain.Announcement;
import com.gyu.domain.Lostarticle;
import com.gyu.service.LostarticleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 失物招领 前端控制器
 * </p>
 *
 * @author choi
 * @since 2023-04-06
 */
@RestController
@Log4j2
@RequestMapping("/lostarticle")
public class LostarticleController {

    @Autowired
    private LostarticleService lostarticleService;

    /**
     * 后台分页查询
     */
    @PostMapping("/getPage")
    public ResultVo getPage(@RequestBody Condition condition) {
        return lostarticleService.getPage(condition);
    }

    /**
     * 后台更新失物招领启用状态
     */
    @PutMapping("/update")
    public ResultVo update(@RequestBody Lostarticle lostarticle) {
        return lostarticleService.update(lostarticle);
    }

    /**
     * 后台模糊查询
     */
    @PostMapping("/querySearch")
    public ResultVo querySearch(@RequestBody Condition condition) {
        return lostarticleService.querySearch(condition);
    }



    /**
     *  小程序获取失物招领所有信息
     */
    @PostMapping("/getLostArticle")
    public ResultVo getLostArticle() {
        return lostarticleService.getLostArticle();
    }

    /**
     *  小程序根据id获取失物招领信息
     */
    @PostMapping("/getOneLostArticle")
    public ResultVo getOneLostArticle(@RequestBody Lostarticle lostarticle) {
        return lostarticleService.getOneLostArticle(lostarticle);
    }

    /**
     * 小程序插入失物信息
     */
    @PostMapping("/insertLostA1")
    public ResultVo insertLostA1(@RequestBody Lostarticle lostarticle) {
        return lostarticleService.insertLostA1(lostarticle);
    }

    /**
     * 小程序插入招领信息
     */
    @PostMapping("/insertLostA2")
    public ResultVo insertLostA2(@RequestBody Lostarticle lostarticle) {
        return lostarticleService.insertLostA2(lostarticle);
    }


}

