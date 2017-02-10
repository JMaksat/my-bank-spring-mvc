package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

import java.util.Date;

@RowMapperClass
public class Directory {

    public static final String ACCOUNTS = "ACCOUNTS";
    public static final String OPERATIONS = "OPERATIONS";
    public static final String ADDRESS = "ADDRESS";
    public static final String CONTACTS = "CONTACTS";
    public static final String PAPERS = "PAPERS";

    @RowMapperField(columnName = "dir_id")
    private Integer dirID;

    @RowMapperField(columnName = "dir_group")
    private String dirGroup;

    @RowMapperField(columnName = "dir_type")
    private String dirType;

    @RowMapperField(columnName = "description")
    private String description;

    @RowMapperField(columnName = "date_created")
    private java.util.Date dateCreated;

    @RowMapperField(columnName = "date_modified")
    private java.util.Date dateModified;

    @RowMapperField(columnName = "is_active")
    private Boolean isActive;

    @RowMapperField(columnName = "user_id")
    private Integer userID;

    public Integer getDirID() {
        return dirID;
    }

    public void setDirID(Integer dirID) {
        this.dirID = dirID;
    }

    public String getDirGroup() {
        return dirGroup;
    }

    public void setDirGroup(String dirGroup) {
        this.dirGroup = dirGroup;
    }

    public String getDirType() {
        return dirType;
    }

    public void setDirType(String dirType) {
        this.dirType = dirType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Directory{" +
                "dirID=" + dirID +
                ", dirGroup='" + dirGroup + '\'' +
                ", dirType='" + dirType + '\'' +
                ", description='" + description + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", isActive=" + isActive +
                ", userID=" + userID +
                '}';
    }
}
