package com.bank.web.model.repository;


import com.bank.web.model.entity.UserRoles;

public interface UserRolesRepository {

    UserRoles findByRole(String s);
}
