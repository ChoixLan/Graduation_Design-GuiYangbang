package com.gyu.controller;

import com.gyu.Vo.Condition;
import com.gyu.Vo.ResultVo;
import com.gyu.domain.Announcement;
import com.gyu.service.AnnouncementService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 公告事务接口
 */
@RestController
@RequestMapping("/findAnnouncement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("/getPage")
    public ResultVo getPage(@RequestBody Condition condition) {
        return announcementService.getPage(condition);
    }


    @PutMapping("/update")
    public ResultVo update(@RequestBody Announcement announcement) {
        return announcementService.update(announcement);
    }

    @PostMapping("/querySearch")
    public ResultVo querySearch(@RequestBody Condition condition) {
        return announcementService.querySearch(condition);
    }

    @PostMapping("/querySearchMini")
    public ResultVo querySearchMini(@RequestBody Condition condition) {
        return announcementService.querySearchMini(condition);
    }

    @PostMapping("/insert")
    public ResultVo insert(@RequestBody Announcement announcement) {
        return announcementService.insert(announcement);
    }

    @PostMapping("/getAnnouncement")
    public ResultVo getAnnouncement() {
        return announcementService.getAnnouncement();
    }

    @PostMapping("/getAnnouncementById")
    public ResultVo getAnnouncementById(@RequestBody Announcement announcement) {
        return announcementService.getAnnouncementById(announcement);
    }


}
