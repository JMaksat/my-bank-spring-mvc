package com.bank.web.model.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "bank.bank_parameters")
public class BankParameters implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parameter_id")
    private Integer parameterId;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "parameter_name")
    private String parameterName;

    @Column(name = "value")
    private String value;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    @Column(name = "active_from")
    private LocalDate activeFrom;

    @Column(name = "active_to")
    private LocalDate activeTo;

    @Column(name = "user_id")
    private String userId;

    public BankParameters() {}

    public BankParameters(Integer parentId, String parameterName, String value, LocalDate dateCreated, LocalDate dateModified, LocalDate activeFrom, LocalDate activeTo, String userId) {
        this.parentId = parentId;
        this.parameterName = parameterName;
        this.value = value;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.activeFrom = activeFrom;
        this.activeTo = activeTo;
        this.userId = userId;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

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

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public LocalDate getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(LocalDate activeFrom) {
        this.activeFrom = activeFrom;
    }

    public LocalDate getActiveTo() {
        return activeTo;
    }

    public void setActiveTo(LocalDate activeTo) {
        this.activeTo = activeTo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getConvertedDate(LocalDate localDate) {

        if (localDate != null)
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return null;
    }

    @Override
    public String toString() {
        return "BankParameters{" +
                "parameterId=" + parameterId +
                ", parentId=" + parentId +
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
