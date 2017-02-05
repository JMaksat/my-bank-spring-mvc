package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

@RowMapperClass
public class Users {

    @RowMapperField(columnName = "username")
    private String UserName;

    @RowMapperField(columnName = "password")
    private String Password;

    @RowMapperField(columnName = "is_active")
    private String IsActive;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }
}
