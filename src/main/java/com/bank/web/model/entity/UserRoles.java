package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

@RowMapperClass
public class UserRoles {

    @RowMapperField(columnName = "user_role_id")
    private Integer UserRoleId;

    @RowMapperField(columnName = "username")
    private String UserName;

    @RowMapperField(columnName = "role")
    private String Role;

    public Integer getUserRoleId() {
        return UserRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        UserRoleId = userRoleId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
