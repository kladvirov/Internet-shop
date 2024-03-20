package by.kladvirov.controller.impl;

import by.kladvirov.controller.CommonControllerTest;
import by.kladvirov.dto.RoleCreationDto;
import by.kladvirov.dto.RoleDto;
import by.kladvirov.exception.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoleControllerTest extends CommonControllerTest {

    @Test
    @DisplayName("Successful find by id test for roles with access to read")
    @WithMockUser(authorities = "READ_ROLES")
    public void findByIdTest_shouldReturnRoleAndStatus200ForUserWithAuthority() throws Exception {
        // given
        Long id = 1L;
        RoleDto roleDto = RoleDto.builder().id(id).name("ADMIN").build();

        // when
        when(roleService.findById(id)).thenReturn(roleDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/roles/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("ADMIN"))
                .andReturn();

        // then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Forbidden find by id test for unauthorized user")
    public void findById_shouldReturnStatus403ForUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/roles/1"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(authorities = "READ_ROLES")
    @DisplayName(" Not found find by id test for user with read authority")
    public void findById_shouldReturnStatus404ForUserWithAuthority() throws Exception {
        // given & when & then
        when(roleService.findById(1000L)).thenThrow(new ServiceException("", HttpStatus.NOT_FOUND));
        mockMvc.perform(MockMvcRequestBuilders.get("/roles/1000"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Successful find all test for roles with access to read")
    @WithMockUser(authorities = "READ_ROLES")
    public void findAllTest_shouldReturnRolesAndStatus200ForUserWithAuthority() throws Exception {
        // given
        List<RoleDto> roles = new ArrayList<>() {{
            add(RoleDto.builder().id(1L).name("ADMIN").build());
            add(RoleDto.builder().id(2L).name("USER").build());
            add(RoleDto.builder().id(3L).name("SELLER").build());
        }};
        Pageable pageable = PageRequest.of(0, 10);

        // when
        when(roleService.findAll(pageable)).thenReturn(roles);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/roles?page=0&size=10"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("ADMIN"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("USER"))
                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[2].name").value("SELLER"))
                .andExpect(status().isOk())
                .andReturn();

        // then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Forbidden find all test for an unauthorized user")
    public void findAllTest_shouldReturnStatus403ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/roles"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "CREATE_ROLES")
    @DisplayName("Successful save test for a user with create authority")
    public void saveTest_shouldReturnRoleDtoAndStatus201ForUserWithAuthority() throws Exception {
        // given
        RoleDto roleWithId = RoleDto.builder().id(3L).name("MANAGER").build();
        RoleCreationDto roleWithoutId = RoleCreationDto.builder().name("MANAGER").build();

        ObjectMapper mapper = new ObjectMapper();

        // when
        when(roleService.save(roleWithoutId)).thenReturn(roleWithId);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/roles")
                        .content(mapper.writeValueAsString(roleWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("MANAGER"))
                .andExpect(status().isCreated())
                .andReturn();

        // then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "CREATE_ROLES")
    @DisplayName("Save test with validation exception for a user with create authority")
    public void saveTestWithNullRole_shouldReturnStatus400ForUserWithAuthority() throws Exception {
        // given
        RoleCreationDto role = RoleCreationDto.builder().build();
        ObjectMapper mapper = new ObjectMapper();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles")
                        .content(mapper.writeValueAsString(role))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "CREATE_ROLES")
    @DisplayName("Save test with validation exception for a user with create authority")
    public void saveTestWithEmptyRole_shouldReturnStatus400ForUserWithAuthority() throws Exception {
        // given
        RoleCreationDto role = RoleCreationDto.builder().name("").build();
        ObjectMapper mapper = new ObjectMapper();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles")
                        .content(mapper.writeValueAsString(role))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Forbidden save test for an unauthorized user")
    public void saveTest_shouldReturnStatus403ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "UPDATE_ROLES")
    @DisplayName("Update test with not found role for user with update authority")
    public void updateTestWithNotFoundRole_shouldReturnStatus400ForUserWithOrderWriteAuthority() throws Exception {
        //given
        RoleCreationDto roleCreationDto = RoleCreationDto.builder().name("USER").build();
        ObjectMapper mapper = new ObjectMapper();

        //when & then
        doThrow(new ServiceException("", HttpStatus.BAD_REQUEST)).when(roleService).update(1000L, roleCreationDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/roles/1000")
                        .content(mapper.writeValueAsString(roleCreationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "UPDATE_ROLES")
    @DisplayName("Update test with validation exception for user with update authority")
    public void updateTestWithNullRole_shouldReturnStatus400ForUserWithOrderWriteAuthority() throws Exception {
        //given
        RoleCreationDto roleCreationDto = RoleCreationDto.builder().build();
        ObjectMapper mapper = new ObjectMapper();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/roles/1")
                        .content(mapper.writeValueAsString(roleCreationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "UPDATE_ROLES")
    @DisplayName("Update test with validation exception for user with update authority")
    public void updateTestWithEmptyRole_shouldReturnStatus400ForUserWithOrderWriteAuthority() throws Exception {
        //given
        RoleCreationDto roleCreationDto = RoleCreationDto.builder().name("").build();
        ObjectMapper mapper = new ObjectMapper();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/roles/1")
                        .content(mapper.writeValueAsString(roleCreationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "UPDATE_ROLES")
    @DisplayName("Successful update test for user with update authority")
    public void updateTest_shouldReturnOrderAndStatus200ForUserWithOrderWriteAuthority() throws Exception {
        //given
        RoleCreationDto roleCreationDto = RoleCreationDto.builder().name("USER").build();
        ObjectMapper mapper = new ObjectMapper();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/roles/1")
                        .content(mapper.writeValueAsString(roleCreationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @WithAnonymousUser
    @DisplayName("Forbidden update test for unauthorized user")
    public void updateTest_shouldReturnStatus403ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/roles/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "DELETE_ROLES")
    @DisplayName("Successful delete test for user with delete authority")
    public void deleteTest_shouldReturnStatus204ForUserWithAuthority() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/roles/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Forbidden delete test for unauthorized user")
    public void deleteTest_shouldReturnStatus403ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/roles/1"))
                .andExpect(status().isForbidden());
    }

}
