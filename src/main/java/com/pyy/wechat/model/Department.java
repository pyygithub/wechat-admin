package com.pyy.wechat.model;

public class Department {
    private Long id;

    private String departmentKey;

    private String departmentValue;

    private String description;

    private String parentDepartmentkey;

    private String createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentKey() {
        return departmentKey;
    }

    public void setDepartmentKey(String departmentKey) {
        this.departmentKey = departmentKey == null ? null : departmentKey.trim();
    }

    public String getDepartmentValue() {
        return departmentValue;
    }

    public void setDepartmentValue(String departmentValue) {
        this.departmentValue = departmentValue == null ? null : departmentValue.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getParentDepartmentkey() {
        return parentDepartmentkey;
    }

    public void setParentDepartmentkey(String parentDepartmentkey) {
        this.parentDepartmentkey = parentDepartmentkey == null ? null : parentDepartmentkey.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
}