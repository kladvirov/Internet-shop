package org.example.service;

import org.example.model.Role;
import org.example.repository.hibernate.RoleRepositoryImpl;

import java.util.List;

public class RoleService {
    private final RoleRepositoryImpl roleRepository;

    public RoleService(RoleRepositoryImpl roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role findRole(Long id) {
        return roleRepository.findById(id);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    public void updateRole(Role role) {
        roleRepository.update(role);
    }

    public void deleteRole(Long id) {
        roleRepository.delete(id);
    }

}