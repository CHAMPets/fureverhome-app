package com.champets.fureverhome.user.service.impl;

import com.champets.fureverhome.user.model.User;
import com.champets.fureverhome.user.model.dto.UserDto;
import com.champets.fureverhome.user.repository.UserRepository;
import com.champets.fureverhome.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

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
    public User findById(Long id) {
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