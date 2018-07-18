package com.pyy.wechat.controller;

import com.pyy.wechat.enums.ResultEnum;
import com.pyy.wechat.service.UserService;
import com.pyy.wechat.vo.Result;
import com.pyy.wechat.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/7/17 0017.
 */
@RestController
@Slf4j
@RequestMapping("/v1/user")
@Api(value = "UserController", description = "系统用户Controller类")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value="用户登录", notes="根据用户名和密码登录")
    @ApiImplicitParam(name = "user", value = "用户登录信息", required = true, dataType = "UserVO", paramType = "body")
    @PostMapping("/login")
    public Result login(@RequestBody UserVO userVO, @RequestHeader HttpHeaders headers) {
        userService.login(userVO, headers);

        return new Result(ResultEnum.LOGIN_SUCCESS);
    }
}
