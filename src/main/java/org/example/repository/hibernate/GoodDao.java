package org.example.repository.hibernate;

import org.example.model.Good;

import java.util.List;

public interface GoodDao {
    public Good findById(Long id);
    public List<Good> findAll();
    public void save(Good good);
    public void update(Good good);
    public void delete(Long id);
}
