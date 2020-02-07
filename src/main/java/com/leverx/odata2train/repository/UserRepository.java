package com.leverx.odata2train.repository;

import com.leverx.odata2train.model.User;

import java.util.List;

public interface UserRepository {

    User findById(long id);

    List<User> findAll();
}
