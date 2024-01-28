package org.example.repository.hibernate;

import org.example.model.Role;

import java.util.List;

public interface RoleRepository {
    public Role findById(Long id);
    public List<Role> findAll();
    public void save(Role role);
    public void update(Role role);
    public void delete(Long id);
}
