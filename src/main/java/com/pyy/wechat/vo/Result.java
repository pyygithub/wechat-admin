package com.pyy.wechat.vo;

import com.pyy.wechat.enums.ResultEnum;
import lombok.Data;

/**
 * Created by Administrator on 2018/7/17 0017.
 */
@Data
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;


    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result OK() {
        return new Result(ResultEnum.SUCCESS);
    }

    public static Result Fail() {
        return new Result(ResultEnum.FAILURE);
    }
}
