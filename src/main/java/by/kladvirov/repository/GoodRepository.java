package by.kladvirov.repository;

import by.kladvirov.model.Good;

import java.util.List;
import java.util.Optional;

public interface GoodRepository {

    Optional<Good> findById(Long id);

    List<Good> findAll(int size, int page);

    Good save(Good good);

    void update(Long id, Good good);

    void delete(Long id);

}
