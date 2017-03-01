package com.bank.web.model.repository;


import com.bank.web.model.entity.UserRoles;

import java.util.List;

public interface UserRolesRepository {

    List<UserRoles> findByRole(String s);
}
