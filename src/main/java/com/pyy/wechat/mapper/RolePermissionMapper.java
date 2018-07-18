package com.pyy.wechat.mapper;

import com.pyy.wechat.model.RolePermission;

public interface RolePermissionMapper {
    int insert(RolePermission record);

    int insertSelective(RolePermission record);
}