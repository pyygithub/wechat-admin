package com.pyy.wechat.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 * Created by Administrator on 2018/7/17 0017.
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private ShiroProperties shiroProperties;

    /**
     * 创建ShiroFilterConfiguration
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加Shiro内置过滤器
        /**
         * Shiro内置管理器，可以实现权限相关的拦截器
         *  常用拦截器：
         *      anon: 无需认证（登录）可以访问
         *      authc: 必须认证才可以访问
         *      user: 如果使用rememberMe的功能可以直接访问
         *      perms： 该资源必须得到资源权权限才能访问
         *      role: 该资源必须得到该角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/v1/user/login", "anon");
        filterMap.put("/*", "authc");

        // 修改跳转的登录页面地址：默认login.jsp
        shiroFilterFactoryBean.setLoginUrl(shiroProperties.getLoginUrl());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }


    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") CustomUserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 加密配置
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");//设置加密算法名称
        matcher.setHashIterations(1);//设置加密次数
        userRealm.setCredentialsMatcher(matcher);

        // 关联Realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean("userRealm")
    public CustomUserRealm customUserRealm() {
        return new CustomUserRealm();
    }
}
