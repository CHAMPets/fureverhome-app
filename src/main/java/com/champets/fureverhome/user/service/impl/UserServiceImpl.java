package com.champets.fureverhome.user.service.impl;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.user.model.User;
import com.champets.fureverhome.user.model.UserRole;
import com.champets.fureverhome.user.model.dto.UserDto;
import com.champets.fureverhome.user.repository.UserRepository;
import com.champets.fureverhome.user.repository.UserRoleRepository;
import com.champets.fureverhome.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.champets.fureverhome.user.model.mapper.UserMapper.mapToUserDto;
import static com.champets.fureverhome.user.model.mapper.UserMapper.mapToUser;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public void createUser(UserDto userDto) {
        User user = new User();
        user.setEmailAddress(userDto.getEmailAddress());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setCreatedDate(userDto.getCreatedDate());
        UserRole role = userRoleRepository.findByRoleName("USER");
        user.setUserRole(role);
        //user.setUserRole((UserRole) Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id).get();
        return mapToUserDto(user);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = mapToUser(userDto);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto findByEmailAddress(String emailAddress) {
        return null;
    }

}