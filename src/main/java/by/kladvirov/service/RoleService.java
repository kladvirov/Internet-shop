package by.kladvirov.service;

import by.kladvirov.dto.RoleCreationDto;
import by.kladvirov.dto.RoleDto;
import by.kladvirov.exception.RepositoryException;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.RoleMapper;
import by.kladvirov.model.Role;
import by.kladvirov.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleDto findById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ServiceException("There is no such role", HttpStatus.NOT_FOUND));
        return roleMapper.toDto(role);
    }

    public List<RoleDto> findAll(int size, int page) {
        try {
            return roleMapper.toDto(roleRepository.findAll(size, page));
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during finding all roles", HttpStatus.BAD_REQUEST);
        }
    }

    public RoleDto save(RoleCreationDto roleDto) {
        try {
            Role entity = roleMapper.toEntity(roleDto);
            return roleMapper.toDto(roleRepository.save(entity));
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during saving role", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(Long id, RoleCreationDto roleDto) {
        try {
            roleRepository.update(id, roleMapper.toEntity(roleDto));
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during updating role", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(Long id) {
        try {
            roleRepository.delete(id);
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during deleting role", HttpStatus.BAD_REQUEST);
        }
    }

}