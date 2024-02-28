package by.kladvirov.repository;

import by.kladvirov.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findById(Long id);

    List<Role> findAll(int size, int page);

    Role save(Role role);

    void update(Long id, Role role);

    void delete(Long id);

}
