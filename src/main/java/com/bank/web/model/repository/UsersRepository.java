package com.bank.web.model.repository;


import com.bank.web.model.entity.Users;

public interface UsersRepository {

    Users findByLogin(String s);
}
