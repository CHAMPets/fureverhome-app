package com.champets.fureverhome.user.model.mapper;

import com.champets.fureverhome.user.model.UserEntity;
import com.champets.fureverhome.user.model.dto.UserDto;
public class UserMapper {
    public static UserDto mapToUserDto(UserEntity user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        return userDto;
    }
}
