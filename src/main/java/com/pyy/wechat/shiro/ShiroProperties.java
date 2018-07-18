package com.pyy.wechat.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/7/17 0017.
 */
@ConfigurationProperties(prefix = "shiro")
@Data
@Component
public class ShiroProperties {

    /**
     * 登录页面地址
     */
    private String loginUrl;

    /**
     * 盐值
     */
    private String salt;
}
