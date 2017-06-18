package com.bank.web.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bank.users")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private Integer isActive;

    @Transient
    private String roles;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userName")
    private Set<UserRoles> userRoles = new HashSet<>(0);

    public Users() {}

    public Users(String userName, String password, Integer isActive, String roles, Set<UserRoles> userRoles) {
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;
        this.roles = roles;
        this.userRoles = userRoles;
    }

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

    public Set<UserRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRoles> userRoles) {
        this.userRoles = userRoles;
    }
}
