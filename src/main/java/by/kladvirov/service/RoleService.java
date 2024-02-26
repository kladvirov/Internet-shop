package by.kladvirov.service;

import by.kladvirov.dto.RoleCreationDto;
import by.kladvirov.dto.RoleDto;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.RoleMapper;
import by.kladvirov.model.Role;
import by.kladvirov.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleDto findById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ServiceException("There is no entity with the following id"));
        return roleMapper.toDto(role);
    }

    public List<RoleDto> findAll(int size, int page) {
        return roleMapper.toDto(roleRepository.findAll(size, page));
    }

    public RoleDto save(RoleCreationDto roleDto) {
        Role entity = roleMapper.toEntity(roleDto);
        return roleMapper.toDto(roleRepository.save(entity));
    }

    public void update(Long id, RoleCreationDto roleDto) {
        roleRepository.update(id, roleMapper.toEntity(roleDto));
    }

    public void delete(Long id) {
        roleRepository.delete(id);
    }

}