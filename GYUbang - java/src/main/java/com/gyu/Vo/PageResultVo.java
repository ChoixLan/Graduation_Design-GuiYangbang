package com.gyu.Vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gyu.domain.User;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResultVo {

    // 当前页码
    private Long Current;

    // 每页显示数
    private Long Size;

    // 一共多少页
    private Long Pages;

    // 一共多少条数据
    private Long Total;

    // 数据
    private List<User> Records;
}
