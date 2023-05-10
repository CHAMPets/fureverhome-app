package com.champets.fureverhome.user.service;

import com.champets.fureverhome.application.model.dto.ApplicationDto;
import com.champets.fureverhome.user.model.User;
import com.champets.fureverhome.user.model.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUsers();

    User saveUser(User user);
    UserDto findById(Long id);

    void updateUser(UserDto userDto);

    List<UserDto> findByEmailAddress(String emailAddress);
}

