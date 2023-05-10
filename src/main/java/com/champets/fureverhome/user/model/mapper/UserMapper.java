package com.champets.fureverhome.user.model.mapper;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;
import com.champets.fureverhome.user.model.User;
import com.champets.fureverhome.user.model.dto.UserDto;

public class UserMapper {
    public static User mapToUser(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .emailAddress(userDto.getEmailAddress())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .lastDateModified(userDto.getLastDateModified())
                .createdBy(userDto.getCreatedBy())
                .lastModifiedBy(userDto.getLastModifiedBy())
                .createdDate(userDto.getCreatedDate())
//                .userRole(userDto.getRoleId())
//                .applications(userDto.getApplication())
                .build();
        return user;
    }
    public static UserDto mapToUserDto(User user) {
        UserDto userDto = UserDto.builder()

                .id(user.getId())
                .emailAddress(user.getEmailAddress())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .lastDateModified(user.getLastDateModified())
                .createdBy(user.getCreatedBy())
                .lastModifiedBy(user.getLastModifiedBy())
                .createdDate(user.getCreatedDate())
//                .userRole(user.getRoleId())
//                .applications(user.getApplication())
                .build();
        return userDto;
    }
}
