package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

@RowMapperClass
public class UserRoles {

    @RowMapperField(columnName = "user_role_id")
    private Integer userRoleId;

    @RowMapperField(columnName = "userName")
    private String userName;

    @RowMapperField(columnName = "role")
    private String role;

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        userRoleId = userRoleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        role = role;
    }

    @Override
    public String toString() {
        return "UserRoles{" +
                "userRoleId=" + userRoleId +
                ", userName='" + userName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
