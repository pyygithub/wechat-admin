package com.pyy.wechat.enums;

import lombok.Getter;

/**
 * 结果集枚举
 *
 * @Author panyy
 * @Date 2018/7/17 0017 18:59
 * @Version 1.0
 */
@Getter
public enum ResultEnum {
    SUCCESS(0, "成功"),
    FAILURE(1, "失败"),
    SYS_ERROR(2, "系统异常"),
    LOGIN_SUCCESS(10, "登录成功"),
    USER_NOT_EXISTS(21, "用户名不存在"),
    PASS_ERROR(22, "密码错误") ;


    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
