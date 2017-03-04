package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

@RowMapperClass(tableName = "bank.users")
public class Users {

    @RowMapperField(columnName = "userName")
    private String userName;

    @RowMapperField(columnName = "password")
    private String password;

    @RowMapperField(columnName = "is_active")
    private Integer isActive;

    @RowMapperField(columnName = "roles")
    private String roles;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", roles='" + roles + '\'' +
                '}';
    }
}
