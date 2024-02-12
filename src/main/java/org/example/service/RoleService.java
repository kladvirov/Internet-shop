package org.example.service;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.example.dto.RoleDto;
import org.example.mapper.RoleMapper;
import org.example.model.Role;
import org.example.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleDto findById(Long id) {
        return roleMapper.toDto(roleRepository.findById(id));
    }

    public List<RoleDto> findAll(int size, int page) {
        return roleRepository.findAll(size, page).stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    public RoleDto save(Role role) {
        return roleMapper.toDto(roleRepository.save(role));
    }

    public void update(Role role) {
        roleRepository.update(role);
    }

    public void delete(Long id) {
        roleRepository.delete(id);
    }

}