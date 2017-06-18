package com.bank.web.model.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "bank.directory")
public class Directory implements Serializable {

    public static final String ACCOUNTS = "ACCOUNTS";
    public static final String OPERATIONS = "OPERATIONS";
    public static final String ADDRESS = "ADDRESS";
    public static final String CONTACTS = "CONTACTS";
    public static final String PAPERS = "PAPERS";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dir_id")
    private Integer dirID;

    @Column(name = "dir_group")
    private String dirGroup;

    @Column(name = "dir_type")
    private String dirType;

    @Column(name = "description")
    private String description;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "user_id")
    private String userID;

    public Directory() {}

    public Directory(String dirGroup, String dirType, String description, LocalDate dateCreated, LocalDate dateModified, Integer isActive, String userID) {
        this.dirGroup = dirGroup;
        this.dirType = dirType;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.isActive = isActive;
        this.userID = userID;
    }

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

    public Date getConvertedDate(LocalDate localDate) {

        if (localDate != null)
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return null;
    }
}
