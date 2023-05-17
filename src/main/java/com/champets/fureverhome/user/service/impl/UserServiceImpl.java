package com.champets.fureverhome.user.service.impl;

import com.champets.fureverhome.exception.user.CurrentUserNotFoundException;
import com.champets.fureverhome.exception.user.UserNotFoundException;
import com.champets.fureverhome.user.model.UserEntity;
import com.champets.fureverhome.user.model.Role;
import com.champets.fureverhome.user.model.dto.RegistrationDto;
import com.champets.fureverhome.user.model.dto.UserDto;
import com.champets.fureverhome.user.repository.UserRepository;
import com.champets.fureverhome.user.repository.RoleRepository;
import com.champets.fureverhome.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.champets.fureverhome.user.model.mapper.UserMapper.mapToUserDto;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("USER");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDto findUserById(Long id) {
        UserEntity user = userRepository.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID not found: " + id);
        }
        return mapToUserDto(user);
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CurrentUserNotFoundException("Current user not found: " + email);
        }
        return user;
    }
}
