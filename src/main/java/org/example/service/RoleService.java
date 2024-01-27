package org.example.service;

import org.example.model.Role;
import org.example.repository.hibernate.RoleDaoImpl;

import java.util.List;

public class RoleService {
    private RoleDaoImpl rolesDao = new RoleDaoImpl();

    public RoleService() {
    }
    public Role findRole(Long id) {
        return rolesDao.findById(id);
    }

    public List<Role> findAllRoles() {
        return rolesDao.findAll();
    }

    public void saveRole(Role role) {
        rolesDao.save(role);
    }

    public void updateRole(Role role) {
        rolesDao.update(role);
    }

    public void deleteRole(Long id) {
        rolesDao.delete(id);
    }

}