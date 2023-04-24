package com.gyu.controller;

import com.gyu.Vo.ResultVo;
import com.gyu.Vo.Condition;
import com.gyu.domain.User;
import com.gyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/findUser")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/getPageUserByClassification")
    public ResultVo getPageUserByClassification(@RequestBody Condition condition) {
        return userService.getPageUserByClassification(condition);
    }

    @PostMapping("/getPageUser")
    public ResultVo getPageUser(@RequestBody Condition condition) {
        return userService.getPageUser(condition);
    }

    @DeleteMapping("/deleteUserById/{id}")
    public  ResultVo deleteUserById(@PathVariable int id) {
        return userService.deleteUserById(id);
    }

    @PostMapping("/insert")
    public ResultVo insert(@RequestBody User user) {
        return userService.insert(user);
    }

    @PutMapping("/update")
    public ResultVo update(@RequestBody User user) {
        return userService.update(user);
    }
}
