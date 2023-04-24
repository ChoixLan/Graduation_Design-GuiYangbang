package com.gyu.controller;


import com.gyu.Vo.Condition;
import com.gyu.Vo.ResultVo;
import com.gyu.domain.Upkeep;
import com.gyu.domain.User;
import com.gyu.service.UpkeepService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 维修 前端控制器
 * </p>
 *
 * @author choi
 * @since 2023-03-19
 */
@RestController
@Log4j2
@RequestMapping("/upkeep")
public class UpkeepController {

    @Autowired
    private UpkeepService upkeepService;

    @PostMapping("/getPage")
    public ResultVo getPage(@RequestBody Condition condition) {
        return upkeepService.getPage(condition);
    }

    @PostMapping("/querySearch")
    public ResultVo querySearch(@RequestBody Condition condition) {
        return upkeepService.querySearch(condition);
    }

    @PutMapping("/update")
    public ResultVo update(@RequestBody Upkeep upkeep) {
        return upkeepService.update(upkeep);
    }

    /**
     * 根据openid获取我的维修申请
     */
    @PostMapping("/getUpkeepInfo")
    public ResultVo getUpkeepInfo(@RequestBody User user) {
        return upkeepService.getUpkeepInfo(user);
    }

    /**
     * 根据openid在数据库插入用户新的维修申请
     */
    @PostMapping("/insertUpkeep")
    public ResultVo insertUpkeep(@RequestBody Upkeep upkeep) {
        log.info(upkeep);
        return upkeepService.insertUpkeep(upkeep);
    }
}

