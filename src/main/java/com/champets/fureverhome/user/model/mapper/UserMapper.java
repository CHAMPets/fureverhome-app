package com.champets.fureverhome.user.model.mapper;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;
import com.champets.fureverhome.user.model.UserEntity;
import com.champets.fureverhome.user.model.dto.RegistrationDto;
import com.champets.fureverhome.user.model.dto.UserDto;

import java.util.Collections;

public class UserMapper {
    public static UserEntity mapToUser(UserDto userDto) {
        UserEntity user = UserEntity.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .createdDate(userDto.getCreatedDate())
                .applications(userDto.getApplications())
                .build();
        return user;
    }
    public static UserDto mapToUserDto(UserEntity user) {
        UserDto userDto = UserDto.builder()

                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdDate(user.getCreatedDate())
                //.applications(user.getApplications())
                .build();
        return userDto;
    }
}
