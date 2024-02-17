package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.RoleCreationDto;
import org.example.dto.RoleDto;
import org.example.mapper.RoleMapper;
import org.example.model.Role;
import org.example.repository.RoleRepository;

import java.util.List;

@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleDto findById(Long id) {
        return roleMapper.toDto(roleRepository.findById(id));
    }

    public List<RoleDto> findAll(int size, int page) {
        return roleMapper.toDto(roleRepository.findAll(size, page));
    }

    public RoleDto save(RoleCreationDto roleDto) {
        Role entity = roleMapper.toEntity(roleDto);
        return roleMapper.toDto(roleRepository.save(entity));
    }

    public void update(RoleCreationDto roleDto) {
        roleRepository.update(roleMapper.toEntity(roleDto));
    }

    public void delete(Long id) {
        roleRepository.delete(id);
    }

}