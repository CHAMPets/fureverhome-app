package com.champets.fureverhome.user.service;

import com.champets.fureverhome.application.model.dto.ApplicationDto;
import com.champets.fureverhome.user.model.User;
import com.champets.fureverhome.user.model.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUsers();

    void createUser(UserDto userDto);
    UserDto findUserById(Long id); //change to findByIdUser

    void updateUser(UserDto userDto);

    void deleteUser(Long id);

    UserDto findByEmailAddress(String emailAddress);
}

