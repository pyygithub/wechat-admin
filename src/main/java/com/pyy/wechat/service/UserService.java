package com.pyy.wechat.service;

import com.pyy.wechat.model.User;
import com.pyy.wechat.vo.UserVO;
import org.springframework.http.HttpHeaders;

import java.util.List;

/**
 * UserService
 *
 * @Author panyy
 * @Date 2018/7/17 0017 18:59
 * @Version 1.0
 */
public interface UserService {

    void login(UserVO userVO, HttpHeaders headers);

    List<String> findRolesByUsername(String username);

    User findByUsername(String username);
}
