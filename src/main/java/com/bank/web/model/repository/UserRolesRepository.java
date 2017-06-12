package com.bank.web.model.repository;


import com.bank.web.model.entity.UserRoles;
import com.bank.web.model.entity.Users;

import java.util.List;

public interface UserRolesRepository {

    List<UserRoles> findByRole(Users s);
}
