package com.gyu.Vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 响应类
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVo<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 查询到的结果数据
     */
    private T data;

    public ResultVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVo(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 响应成功的结果
     * @param message
     * @return
     */
    public static ResultVo success(String message) {
        return new ResultVo(200, message);
    }

    /**
     * 响应成功的结果
     * @param message
     * @param data
     * @return
     */
    public static ResultVo success(String message, Object data) {
        return new ResultVo(200, message, data);
    }


    /**
     * 响应失败的结果
     * @param message
     * @return
     */
    public static ResultVo fail(String message) {
        return new ResultVo(301, message);
    }
}
