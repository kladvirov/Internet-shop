package org.example.service;

import org.example.dto.RoleDto;
import org.example.mapper.RoleMapper;
import org.example.model.Role;
import org.example.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleDto findRole(Long id) {
        return new RoleMapper().roleToDto(roleRepository.findById(id));
    }

    public List<RoleDto> findAllRoles(int size, int page) {
        RoleMapper roleMapper = new RoleMapper();
        return roleRepository.findAll(size, page).stream()
                .map(roleMapper::roleToDto)
                .collect(Collectors.toList());
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public void updateRole(Role role) {
        roleRepository.update(role);
    }

    public void deleteRole(Long id) {
        roleRepository.delete(id);
    }

}