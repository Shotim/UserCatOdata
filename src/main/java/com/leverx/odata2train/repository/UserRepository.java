package com.leverx.odata2train.repository;

import java.util.List;
import java.util.Map;

public interface UserRepository {

    Map<String, Object> getById(long id);

    List<Map<String, Object>> getAll();
}
