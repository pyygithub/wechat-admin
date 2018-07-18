# 微信后台管理项目

### 环境搭建

### Shiro核心API

1. Subject: 用户主体（把操作交给SecurityManager）
2. SecurityManager： 安全管理器（管理Realm）
3. Realm：Shiro连接数据的桥梁

### SpringBoot和Shiro整合

1.  导入Shiro和SpringBoot依赖包：

```
    <dependency>
        <groupId>org.apache.shiro</groupId>
        <artifactId>shiro-spring</artifactId>
        <version>1.4.0</version>
    </dependency>
```

2. 自定义Realm类
```java
package com.pyy.wechat.shiro;

import com.pyy.wechat.Exception.WechatException;
import com.pyy.wechat.enums.ResultEnum;
import com.pyy.wechat.model.User;
import com.pyy.wechat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 自定义Realm
 * Created by panyy on 2018/7/17 0017.
 */
@Slf4j
public class CustomUserRealm extends AuthorizingRealm {
    public static void main(String[] args) {
        // 加salt密码
        Md5Hash md5Hash = new Md5Hash("123456", "pyy");
        System.out.println(md5Hash);
    }

    @Autowired
    private ShiroProperties shiroProperties;

    @Autowired
    private UserService userService;

    /**
     * 执行授权逻辑
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();

        // 从数据库中根据用户名获取角色数据
        Set<String> roles = getRolesByUsername(username);
        // 从数据库中根据用户名获取权限数据
        Set<String> permissions = getPermissionbyUsername(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * 执行认证逻辑
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        // 1.判断用户名是否存在
        if(!isExists(username)){
            // Shiro底层会抛出：UnKnowAccountException
            return null;
        }
        // 2.判断密码是否存在
        String password = getPasswordByUsername(usernamePasswordToken.getUsername());
        // 这里shiro会自动验证密码如果密码和数据库不一致会抛出：IncorrectCredentialsException
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, "");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(shiroProperties.getSalt()));//设置盐salt

        return authenticationInfo;
    }

    /**
     * 根据用户名查询用户是否存在
     * @param username
     * @return
     */
    private boolean isExists(String username) {
        User user = userService.findByUsername(username);
        if(user != null) {
            log.info("【用户管理】根据用户名查询用户信息，user={}", user);
            return true;
        }
        log.info("【用户管理】根据用户名查询用户信息失败，用户不存在");
        return false;
    }

    /**
     * 根据用户名获取密码
     * @param username
     * @return
     */
    private String getPasswordByUsername(String username) {
        User user = userService.findByUsername(username);
        if(user != null) {
            log.info("【用户管理】根据用户名查询密码信息，user={}", user);
            return user.getLoginPass();
        }
        log.error("【用户管理】根据用户名查询异常");
        throw new WechatException(ResultEnum.SYS_ERROR);
    }

    /**
     * 根据用户名获取用户权限集合
     * @param username
     * @return
     */
    private Set<String> getPermissionbyUsername(String username) {
        return null;
    }

    /**
     * 根据用户名获取用户角色集合
     * @param username
     * @return
     */
    private Set<String> getRolesByUsername(String username) {
        return null;
    }


}

```


3. 编写Shiro配置类：

```java
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

```



