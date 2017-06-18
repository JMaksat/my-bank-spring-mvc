package com.bank.web.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bank.user_roles")
public class UserRoles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Integer userRoleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private Users userName;

    @Column(name = "user_role")
    private String userRole;

    public UserRoles() {}

    public UserRoles(Users userName, String userRole) {
        this.userName = userName;
        this.userRole = userRole;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Users getUserName() {
        return userName;
    }

    public void setUserName(Users userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
