package com.pyy.wechat.Exception;

import com.pyy.wechat.enums.ResultEnum;
import lombok.Data;

/**
 * 自定义异常类
 *
 * @Author panyy
 * @Date 2018/7/17 0017 19:21
 * @Version 1.0
 */
@Data
public class WechatException extends RuntimeException {
    private Integer code;
    private String msg;

    public WechatException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public WechatException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }
}
