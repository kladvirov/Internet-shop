package by.kladvirov.controller;

import by.kladvirov.dto.RoleCreationDto;
import by.kladvirov.dto.RoleDto;
import by.kladvirov.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public RoleDto getById(@PathVariable("id") Long id) {
        return roleService.findById(id);
    }

    @GetMapping()
    public List<RoleDto> getAllRoles(@RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return roleService.findAll(size, page);
    }


    @PostMapping()
    public RoleDto createRole(@RequestBody @Valid RoleCreationDto roleCreationDto) {
        return roleService.save(roleCreationDto);
    }

    @PutMapping("{id}")
    public String update(@PathVariable("id") Long id, @RequestBody @Valid RoleCreationDto roleCreationDto) {
        roleService.update(id, roleCreationDto);
        return "Role has been updated successfully";
    }

    @DeleteMapping("{id}")
    public String deleteById(@PathVariable("id") Long id) {
        roleService.delete(id);
        return "Role has been deleted successfully";
    }

}
