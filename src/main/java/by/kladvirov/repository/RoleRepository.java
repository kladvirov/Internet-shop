package by.kladvirov.repository;

import by.kladvirov.model.Role;

import java.util.List;

public interface RoleRepository {

    Role findById(Long id);

    List<Role> findAll(int size, int page);

    Role save(Role role);

    void update(Role role);

    void delete(Long id);

}
