package by.kladvirov.controller;

import by.kladvirov.dto.RoleCreationDto;
import by.kladvirov.dto.RoleDto;
import by.kladvirov.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@Tag(name = "Role controller", description = "Role API")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ROLES')")
    @Operation(summary = "Get role by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Role not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<RoleDto> getById(
            @PathVariable("id")
            @Parameter(description = "Role id", example = "11", required = true) Long id
    ) {
        return new ResponseEntity<>(roleService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ROLES')")
    @Operation(summary = "Get all roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Roles not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<RoleDto>> getAllRoles(Pageable pageable) {
        return new ResponseEntity<>(roleService.findAll(pageable), HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ROLES')")
    @Operation(summary = "Create new role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role has been created successfully"),
            @ApiResponse(responseCode = "400", description = "Role hasn't been created. Check the data validity"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<RoleDto> createRole(
            @RequestBody
            @Valid
            @Parameter(description = "Info towards creating role", required = true) RoleCreationDto roleCreationDto
    ) {
        return new ResponseEntity<>(roleService.save(roleCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ROLES')")
    @Operation(summary = "Update role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Role hasn't been updated. Check the data validity"),
            @ApiResponse(responseCode = "404", description = "Role not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HttpStatus> update(
            @PathVariable("id")
            @Parameter(description = "Role id", example = "228", required = true) Long id,
            @RequestBody
            @Valid
            @Parameter(description = "Info towards updating role", required = true) RoleCreationDto roleCreationDto
    ) {
        roleService.update(id, roleCreationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ROLES')")
    @Operation(summary = "Delete role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Role has been deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Role hasn't been deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HttpStatus> deleteById(
            @PathVariable("id")
            @Parameter(description = "Role id", example = "11", required = true) Long id
    ) {
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
