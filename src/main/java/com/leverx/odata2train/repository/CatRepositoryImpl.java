package com.leverx.odata2train.repository;

import com.leverx.odata2train.model.Cat;
import com.leverx.odata2train.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

@Repository(value = "catRepository")
public class CatRepositoryImpl implements CatRepository {

    public Cat findById(long id) {
        if (id == 1) {
            Cat cat = new Cat(1, "Cat", null);
            User user = new User(1, "User", singletonList(cat));
            cat.setOwner(user);
            return cat;
        }
        return null;
    }

    public List<Cat> findAll() {
        final ArrayList<Cat> cats = new ArrayList<>();
        cats.add(findById(1));
        return cats;
    }
}
