package com.bank.web.model.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "bank.customer_papers")
public class CustomerPapers implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paper_id")
    private Integer paperID;

    @Column(name = "value")
    private String value;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "paper_type")
    private Integer paperType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private CustomerInfo customer;

    @Transient
    private String paperTypeLabel;

    public CustomerPapers() {}

    public CustomerPapers(String value, LocalDate dateCreated, LocalDate dateModified, Integer isActive, String userID, Integer paperType, CustomerInfo customer, String paperTypeLabel) {
        this.value = value;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.isActive = isActive;
        this.userID = userID;
        this.paperType = paperType;
        this.customer = customer;
        this.paperTypeLabel = paperTypeLabel;
    }

    public Integer getPaperID() {
        return paperID;
    }

    public void setPaperID(Integer paperID) {
        this.paperID = paperID;
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getPaperType() {
        return paperType;
    }

    public void setPaperType(Integer paperType) {
        this.paperType = paperType;
    }

    public CustomerInfo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerInfo customer) {
        this.customer = customer;
    }

    public String getPaperTypeLabel() {
        return paperTypeLabel;
    }

    public void setPaperTypeLabel(String paperTypeLabel) {
        this.paperTypeLabel = paperTypeLabel;
    }

    public Date getConvertedDate(LocalDate localDate) {

        if (localDate != null)
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return null;
    }

    @Override
    public String toString() {
        return "CustomerPapers{" +
                "paperID=" + paperID +
                ", value='" + value + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", isActive=" + isActive +
                ", userID='" + userID + '\'' +
                ", paperType=" + paperType +
                ", customer=" + customer +
                ", paperTypeLabel='" + paperTypeLabel + '\'' +
                '}';
    }
}
