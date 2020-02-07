package com.leverx.odata2train.repository;

import com.leverx.odata2train.model.Cat;
import com.leverx.odata2train.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Repository(value = "userRepository")
public class UserRepositoryImpl implements UserRepository {

    public User findById(long id) {
        if (id == 1) {
            User user = new User(1, "User", emptyList());
            Cat cat1 = new Cat(1, "1", user);
            Cat cat2 = new Cat(2, "2", user);
            Cat cat3 = new Cat(1, "3", user);
            user.setCats(asList(cat1, cat2, cat3));
            return user;
        }
        return null;
    }

    public List<User> findAll() {
        final ArrayList<User> users = new ArrayList<>();
        users.add(findById(1));
        return users;
    }
}
