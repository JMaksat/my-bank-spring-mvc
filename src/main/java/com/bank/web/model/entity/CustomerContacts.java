package com.bank.web.model.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "bank.customer_contacts")
public class CustomerContacts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Integer contactID;

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

    @Column(name = "contact_type")
    private Integer contactType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private CustomerInfo customer;

    @Transient
    private String contactTypeLabel;

    public CustomerContacts() {}

    public CustomerContacts(String value, LocalDate dateCreated, LocalDate dateModified, Integer isActive, String userID, Integer contactType, CustomerInfo customer, String contactTypeLabel) {
        this.value = value;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.isActive = isActive;
        this.userID = userID;
        this.contactType = contactType;
        this.customer = customer;
        this.contactTypeLabel = contactTypeLabel;
    }

    public Integer getContactID() {
        return contactID;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
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

    public Integer getContactType() {
        return contactType;
    }

    public void setContactType(Integer contactType) {
        this.contactType = contactType;
    }

    public CustomerInfo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerInfo customer) {
        this.customer = customer;
    }

    public String getContactTypeLabel() {
        return contactTypeLabel;
    }

    public void setContactTypeLabel(String contactTypeLabel) {
        this.contactTypeLabel = contactTypeLabel;
    }

    public Date getConvertedDate(LocalDate localDate) {

        if (localDate != null)
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return null;
    }
}
