package com.leverx.odata2train.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

@Repository
public class UserRepositoryImpl implements UserRepository {

    public Map<String, Object> getById(long id) {
        if (id == 1) {
            HashMap<String, Object> user = new HashMap<>();
            user.put("Id", 1);
            user.put("Name", "Steve");
            user.put("Cats", emptyList());
            return user;
        }
        return emptyMap();
    }

    public List<Map<String, Object>> getAll() {
        final ArrayList<Map<String, Object>> objects = new ArrayList<>();
        objects.add(getById(1));
        return objects;
    }
}
