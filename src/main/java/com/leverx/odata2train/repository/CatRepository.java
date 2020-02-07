package com.leverx.odata2train.repository;

import com.leverx.odata2train.model.Cat;

import java.util.List;

public interface CatRepository {

    Cat findById(long id);

    List<Cat> findAll();
}
