package com.pyy.wechat.Exception;

import com.pyy.wechat.enums.ResultEnum;
import com.pyy.wechat.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 *
 * @Author panyy
 * @Date 2018/7/18 0018 16:39
 * @Version 1.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        if(e instanceof WechatException) {
            WechatException wechatException = (WechatException) e;
            return new Result(wechatException.getCode(), wechatException.getMsg());
        }
        return new Result(ResultEnum.SYS_ERROR);
    }
}
