package com.champets.fureverhome.user.service;

import com.champets.fureverhome.pet.PetDto;
import com.champets.fureverhome.user.model.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUsers();
}

