package eu.accesa.learningplatform.controller;

import eu.accesa.learningplatform.model.dto.UserDto;
import eu.accesa.learningplatform.model.dto.UserDtoForApplication;
import eu.accesa.learningplatform.model.dto.UserDtoForGetCalls;
import eu.accesa.learningplatform.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@OpenAPIDefinition
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private UserDto createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<UserDtoForGetCalls> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private UserDtoForGetCalls getUserById(@PathVariable final Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/userType/{userTypeId}")
    @ResponseStatus(HttpStatus.OK)
    private List<UserDtoForGetCalls> getUsersByUserType(@PathVariable final Long userTypeId) {
        return userService.getUsersByUserType(userTypeId);
    }

    @GetMapping("/program/{programId}")
    @ResponseStatus(HttpStatus.OK)
    private List<UserDtoForGetCalls> getUsersByProgramId(@PathVariable final Long programId) {
        return userService.getAllUsersByProgram(programId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    private UserDto updateUser(@Valid @RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @PutMapping("/userType/update")
    @ResponseStatus(HttpStatus.OK)
    private UserDtoForApplication updateUserType(@Valid @RequestBody UserDtoForApplication userDto) {
        return userService.updateUserType(userDto);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteUser(@PathVariable final Long id) {
        userService.deleteUser(id);
    }
}
