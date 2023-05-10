package com.champets.fureverhome.user.service.impl;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.user.model.User;
import com.champets.fureverhome.user.model.dto.UserDto;
import com.champets.fureverhome.user.repository.UserRepository;
import com.champets.fureverhome.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

import static com.champets.fureverhome.user.model.mapper.UserMapper.mapToUserDto;
import static com.champets.fureverhome.user.model.mapper.UserMapper.mapToUser;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).get();
        return mapToUserDto(user);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = mapToUser(userDto);
        userRepository.save(user);
    }

    @Override
    public List<UserDto> findByEmailAddress(String emailAddress) {
        return null;
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .emailAddress(user.getEmailAddress())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roleId(user.getUserRole().getId())
                .lastDateModified(user.getLastDateModified())
                .createdBy(user.getCreatedBy())
                .lastModifiedBy(user.getLastModifiedBy())
                .createdDate(user.getCreatedDate())
                .build();
        return userDto;
    }
}