package com.gyu.controller;


import com.gyu.Vo.Condition;
import com.gyu.Vo.ResultVo;
import com.gyu.domain.UToU;
import com.gyu.service.UToUService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 快递代领 前端控制器
 * </p>
 *
 * @author choi
 * @since 2023-03-20
 */
@RestController
@Log4j2
@RequestMapping("/uToU")
public class UToUController {

    @Autowired
    private UToUService uToUService;

    /**
     * 后台分页查询
     */
    @PostMapping("/getPage")
    public ResultVo getPage(@RequestBody Condition condition) {
        return uToUService.getPage(condition);
    }

    /**
     * 后台搜索
     */
    @PostMapping("/querySearch")
    public ResultVo querySearch(@RequestBody Condition condition) {
        return uToUService.querySearch(condition);
    }

    @PutMapping("/update")
    public ResultVo update(@RequestBody UToU uToU) {
        return uToUService.update(uToU);
    }

    /**
     * 小程序获取公告快递代领发布列表
     * @return
     */
    @PostMapping("/getUToU")
    public ResultVo getUToU() {
        return uToUService.getUToU();
    }

    /**
     * 小程序用户接取快递代领悬赏
     */
    @PostMapping("/ReceivingUToU")
    public ResultVo ReceivingUToU(@RequestBody UToU uToU) {
        return uToUService.ReceivingUToU(uToU);
    }

    /**
     * 根据openid获取小程序用户的快递代领悬赏
     */
    @PostMapping("/getMyUToU")
    public ResultVo getMyUToU(@RequestBody UToU uToU) {
        return uToUService.getMyUToU(uToU);

    }

    /**
     * 获取小程序用户接取的快递代领悬赏
     */
    @PostMapping("/getMyQUToU")
    public ResultVo getMyQUToU(@RequestBody UToU uToU) {
        return uToUService.getMyQUToU(uToU);

    }

    /**
     *  小程序用户确认悬赏订单完成
     */
    @PostMapping("/OkMyUToU")
    public ResultVo OkMyUToU(@RequestBody UToU uToU) {
        return uToUService.OkMyUToU(uToU);
    }

    /**
     *  小程序用户确认取消悬赏订单
     */
    @PostMapping("/qMyUToU")
    public ResultVo qMyUToU(@RequestBody UToU uToU) {
        return uToUService.qMyUToU(uToU);
    }

    /**
     *  插入新的悬赏
     */
    @PostMapping("/insertUToU")
    public ResultVo insertUToU(@RequestBody UToU uToU) {
        return uToUService.insertUToU(uToU);
    }
}

