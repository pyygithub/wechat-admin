package com.pyy.wechat.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 用户信息VO类
 * @Author panyy
 * @Date 2018/7/17 0017 19:06
 * @Version 1.0
 */
@Data
@ApiModel(value = "UserVO", description = "用户信息VO类")
public class UserVO {
    /**
     * 登录账户名称
     */
    private String username;

    /**
     * 登录账户密码
     */
    private String password;

    /**
     * 登录用户的APPID
     */
    private String appId;

}
