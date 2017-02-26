package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

import java.util.Date;

@RowMapperClass(tableName = "bank.bank_parameters")
public class BankParameters {

    @RowMapperField(columnName = "parent_id")
    private Integer parentId;

    @RowMapperField(columnName = "parameter_name")
    private String parameterName;

    @RowMapperField(columnName = "value")
    private String value;

    @RowMapperField(columnName = "date_created")
    private Date dateCreated;

    @RowMapperField(columnName = "date_modified")
    private Date dateModified;

    @RowMapperField(columnName = "active_from")
    private Date activeFrom;

    @RowMapperField(columnName = "active_to")
    private Date activeTo;

    @RowMapperField(columnName = "user_id")
    private String userId;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Date getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(Date activeFrom) {
        this.activeFrom = activeFrom;
    }

    public Date getActiveTo() {
        return activeTo;
    }

    public void setActiveTo(Date activeTo) {
        this.activeTo = activeTo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "BankParameters{" +
                "parentId=" + parentId +
                ", parameterName='" + parameterName + '\'' +
                ", value='" + value + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", activeFrom=" + activeFrom +
                ", activeTo=" + activeTo +
                ", userId='" + userId + '\'' +
                '}';
    }
}
