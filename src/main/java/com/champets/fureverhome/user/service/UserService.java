package com.champets.fureverhome.user.service;

import com.champets.fureverhome.user.model.UserEntity;
import com.champets.fureverhome.user.model.dto.RegistrationDto;
import com.champets.fureverhome.user.model.dto.UserDto;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    UserEntity findByEmail(String email);

//    void updateUser(UserDto userDto);
//    void deleteUser(Long id);
//    List<UserDto> findAllUsers();
    //UserEntity findByUsername(String username);
    UserDto findUserById(Long id); //change to findByIdUser
}

