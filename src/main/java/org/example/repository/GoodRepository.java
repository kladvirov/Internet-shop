package org.example.repository;

import org.example.model.Good;

import java.util.List;

public interface GoodRepository {

    Good findById(Long id);

    List<Good> findAll(int size, int page);

    Good save(Good good);

    void update(Good good);

    void delete(Long id);

}
