package org.example.repository;

import org.example.model.Role;

import java.util.List;

public interface RoleRepository {
    public Role findById(Long id);
    public List<Role> findAll(int size, int page);
    public Role save(Role role);
    public void update(Role role);
    public void delete(Long id);
}
