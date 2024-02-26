package by.kladvirov.controller;

import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping()
    public List<UserDto> getAllUsers(@RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return userService.findAll(size, page);
    }


    @PostMapping()
    public UserDto createUser(@RequestBody @Valid UserCreationDto userCreationDto) {
        return userService.save(userCreationDto);
    }

    @PutMapping("{id}")
    public String update(@PathVariable("id") Long id, @RequestBody @Valid UserCreationDto userCreationDto) {
        userService.update(id, userCreationDto);
        return "User has been updated successfully";
    }

    @DeleteMapping("{id}")
    public String deleteById(@PathVariable("id") Long id) {
        userService.delete(id);
        return "User has been deleted successfully";
    }

}
