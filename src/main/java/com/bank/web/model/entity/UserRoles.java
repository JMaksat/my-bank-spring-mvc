package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

@RowMapperClass(tableName = "bank.user_roles")
public class UserRoles {

    @RowMapperField(columnName = "user_role_id")
    private Integer userRoleId;

    @RowMapperField(columnName = "userName")
    private String userName;

    @RowMapperField(columnName = "user_role")
    private String userRole;

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "UserRoles{" +
                "userRoleId=" + userRoleId +
                ", userName='" + userName + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
