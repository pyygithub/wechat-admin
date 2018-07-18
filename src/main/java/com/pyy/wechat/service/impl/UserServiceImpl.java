package com.pyy.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.pyy.wechat.Exception.WechatException;
import com.pyy.wechat.enums.ResultEnum;
import com.pyy.wechat.mapper.UserMapper;
import com.pyy.wechat.model.User;
import com.pyy.wechat.service.UserService;
import com.pyy.wechat.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户service接口实现类
 *
 * @Author panyy
 * @Date 2018/7/17 0017 19:23
 * @Version 1.0
 */
@Service("userService")
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public void login(UserVO userVO, HttpHeaders headers) {
        // 1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(userVO.getUsername(), userVO.getPassword());
        // 3.执行登录方法
        try {
            subject.login(token);
            log.info("【用户管理】登录成功，token={}", JSON.toJSONString(token));
        } catch (UnknownAccountException e) {

            log.error("【用户管理】登录失败，用户名不存在");
            throw new WechatException(ResultEnum.USER_NOT_EXISTS);
        } catch (IncorrectCredentialsException e) {

            log.error("【用户管理】登录失败，密码错误");
            throw new WechatException(ResultEnum.PASS_ERROR);
        }catch (Exception e) {
            log.error("【用户管理】系统过异常，e={}", e);
            throw new WechatException(ResultEnum.SYS_ERROR);
        }
    }

    @Override
    public List<String> findRolesByUsername(String username) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        User user = userMapper.selectByUsername(username);
        return user;
    }
}
