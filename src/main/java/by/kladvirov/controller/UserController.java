package by.kladvirov.controller;

import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final ObjectMapper objectMapper;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String getById(@PathVariable("id") Long id) throws JsonProcessingException {
        UserDto user = userService.findById(id);
        return objectMapper.writeValueAsString(user);
    }

    @GetMapping()
    @ResponseBody
    public String getAllUsers(@RequestParam(value = "size", required = false, defaultValue = "10") int size,
                              @RequestParam(value = "page", required = false, defaultValue = "0") int page)
            throws JsonProcessingException {
        List<UserDto> users = userService.findAll(size, page);
        return objectMapper.writeValueAsString(users);
    }


    @PostMapping("/create")
    @ResponseBody
    public String createUser(@RequestBody UserCreationDto userCreationDto) throws JsonProcessingException {
        UserDto user = userService.save(userCreationDto);
        return objectMapper.writeValueAsString(user);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public String update(@PathVariable("id") Long id, @RequestBody UserCreationDto userCreationDto) {
        userService.update(id, userCreationDto);
        return "User has been updated successfully";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteById(@PathVariable("id") Long id) {
        userService.delete(id);
        return "User has been deleted successfully";
    }

}
