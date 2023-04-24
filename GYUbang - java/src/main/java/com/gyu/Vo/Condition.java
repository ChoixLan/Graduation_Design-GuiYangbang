package com.gyu.Vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Condition类主要是存储查询条件的
 * 所以它的属性就是前端传入的查询条件。
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Condition {
    private String pageCurrent;
    private String pageSize;
    private String classification;
    private String queryString;
}
