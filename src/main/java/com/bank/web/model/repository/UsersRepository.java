package com.bank.web.model.repository;


import com.bank.web.model.entity.Users;

import java.util.List;

public interface UsersRepository {

    List<Users> getUsers();

    Users findByLogin(String s);
}
