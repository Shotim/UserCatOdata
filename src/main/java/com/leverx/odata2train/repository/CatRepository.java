package com.leverx.odata2train.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

public class CatRepository {

    public Map<String, Object> getById(long id) {
        if (id == 1) {
            HashMap<String, Object> cat = new HashMap<>();
            cat.put("Id", 1);
            cat.put("Name", "Steve");
            cat.put("Owner", null);
            return cat;
        }
        return emptyMap();
    }

    public List<Map<String, Object>> getAll() {
        final ArrayList<Map<String, Object>> objects = new ArrayList<>();
        objects.add(getById(1));
        return objects;
    }
}
