package com.pyy.wechat.model;

import lombok.Data;

@Data
public class User {
    private Long userId;

    private String openId;

    private String loginAccount;

    private String loginPass;

    private String userName;

    private String userHead;

    private String userPhone;

    private String userEmail;

    private Integer userSex;

    private String userBirthday;

    private String registerTime;

    private String departmentKey;

}