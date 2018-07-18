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
