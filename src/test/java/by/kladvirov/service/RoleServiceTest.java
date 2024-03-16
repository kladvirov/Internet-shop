package by.kladvirov.service;

import by.kladvirov.dto.RoleCreationDto;
import by.kladvirov.dto.RoleDto;
import by.kladvirov.mapper.RoleMapper;
import by.kladvirov.model.Authority;
import by.kladvirov.model.Role;
import by.kladvirov.model.User;
import by.kladvirov.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

    @InjectMocks
    private RoleService roleService;

    @Test
    void findById_ReturnsRoleDto() {

        Long id = 1L;
        RoleDto expected = RoleDto.builder().id(id).name("ADMIN").build();

        when(roleRepository.findById(id)).thenReturn(Optional.of(Role.builder()
                .id(id)
                .authorities(new HashSet<>() {{
                    add(Authority.builder().id(1L).build());
                }})
                .users(new HashSet<>() {{
                    add(User.builder().id(34L).build());
                }})
                .name("ADMIN")
                .build()));

        RoleDto actual = roleService.findById(id);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll_ReturnsListOfRoleDto() {

        Pageable pageable = PageRequest.of(0, 3);

        List<RoleDto> expected = new ArrayList<>() {{
            add(RoleDto.builder().id(1L).build());
            add(RoleDto.builder().id(2L).build());
            add(RoleDto.builder().id(3L).build());
        }};
        List<Role> roles = new ArrayList<>(){{
            add(Role.builder().id(1L).build());
            add(Role.builder().id(2L).build());
            add(Role.builder().id(3L).build());
        }};

        Page<Role> pageRoles = new PageImpl<>(roles);

        when(roleRepository.findAll(pageable)).thenReturn(pageRoles);

        List<RoleDto> actual = roleService.findAll(pageable);

         Assertions.assertEquals(expected, actual);
    }

    @Test
    void add_ReturnsAddedRole() {

        RoleDto expected = RoleDto.builder().id(4L).name("BROKE").build();
        Role roleWithoutId = Role.builder().name("BROKE").build();
        Role roleWithId = Role.builder().id(4L).name("BROKE").build();

        when(roleRepository.save(roleWithoutId)).thenReturn(roleWithId);

        RoleDto actual = roleService.save(RoleCreationDto.builder().name("BROKE").build());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update_UpdatesRole() {

        Long id = 4L;
        RoleCreationDto roleCreationDto = RoleCreationDto.builder().name("ENGINEER").build();
        Role role = Role.builder().id(id).name("ENGINEER").build();

        when(roleRepository.findById(id)).thenReturn(Optional.of(role));

        roleService.update(id, roleCreationDto);

        verify(roleRepository, times(1)).findById(id);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void delete_DeletesRole() {

        Long id = 4L;
        roleService.delete(id);

        verify(roleRepository, times(1)).deleteById(id);
    }

}
