package org.example.repository;

import org.example.model.Good;

import java.util.List;

public interface GoodRepository {
    public Good findById(Long id);
    public List<Good> findAll();
    public Good save(Good good);
    public void update(Good good);
    public void delete(Long id);
}
