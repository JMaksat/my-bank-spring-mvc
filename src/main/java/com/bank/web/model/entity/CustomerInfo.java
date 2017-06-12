package com.bank.web.model.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bank.customer_info")
public class CustomerInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "accountOwner")
    private Set<Accounts> accounts = new HashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private Set<CustomerAddress> addresses = new HashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private Set<CustomerContacts> contacts = new HashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private Set<CustomerPapers> papers = new HashSet<>(0);

    public CustomerInfo() {}

    public CustomerInfo(String firstName, String lastName, String middleName, LocalDate birthDate, LocalDate dateModified, Integer isActive, String userID, LocalDate dateCreated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.dateModified = dateModified;
        this.isActive = isActive;
        this.userID = userID;
        this.dateCreated = dateCreated;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Set<Accounts> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Accounts> accounts) {
        this.accounts = accounts;
    }

    public Set<CustomerAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<CustomerAddress> addresses) {
        this.addresses = addresses;
    }

    public Set<CustomerContacts> getContacts() {
        return contacts;
    }

    public void setContacts(Set<CustomerContacts> contacts) {
        this.contacts = contacts;
    }

    public Set<CustomerPapers> getPapers() {
        return papers;
    }

    public void setPapers(Set<CustomerPapers> papers) {
        this.papers = papers;
    }

    public Date getConvertedDate(LocalDate localDate) {

        if (localDate != null)
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return null;
    }

    @Override
    public String toString() {
        return "CustomerInfo{" +
                "customerID=" + customerID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthDate=" + birthDate +
                ", dateModified=" + dateModified +
                ", isActive=" + isActive +
                ", userID='" + userID + '\'' +
                ", dateCreated=" + dateCreated +
                ", accounts=" + accounts +
                ", addresses=" + addresses +
                ", contacts=" + contacts +
                ", papers=" + papers +
                '}';
    }
}
