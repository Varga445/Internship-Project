package eu.accesa.learningplatform.service;

import eu.accesa.learningplatform.model.dto.UserDto;
import eu.accesa.learningplatform.model.dto.UserDtoForApplication;
import eu.accesa.learningplatform.model.dto.UserDtoForGetCalls;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDtoForGetCalls getUserById(Long id);

    List<UserDtoForGetCalls> getUsersByUserType(Long userTypeId);

    List<UserDtoForGetCalls> getAllUsers();

    List<UserDtoForGetCalls> getAllUsersByProgram(Long programId);

    UserDto updateUser(UserDto userDto);

    UserDtoForApplication updateUserType(UserDtoForApplication userDtoForApplication);

    void deleteUser(Long id);
}
