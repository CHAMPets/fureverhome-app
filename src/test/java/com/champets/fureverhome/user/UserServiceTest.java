package com.champets.fureverhome.user;

import com.champets.fureverhome.user.model.Role;
import com.champets.fureverhome.user.model.UserEntity;
import com.champets.fureverhome.user.model.dto.RegistrationDto;
import com.champets.fureverhome.user.model.dto.UserDto;
import com.champets.fureverhome.user.repository.UserRepository;
import com.champets.fureverhome.user.repository.RoleRepository;
import com.champets.fureverhome.user.service.UserService;
import com.champets.fureverhome.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_ShouldSaveUser() {
        // Arrange
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setUsername("testuser");
        registrationDto.setEmail("testuser@example.com");
        registrationDto.setFirstName("John");
        registrationDto.setLastName("Doe");
        registrationDto.setPhoneNumber("123456789");
        registrationDto.setPassword("password");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setEmail("testuser@example.com");
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setPhoneNumber("123456789");

        Role role = new Role();
        role.setName("USER");

        when(passwordEncoder.encode(registrationDto.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName("USER")).thenReturn(role);

        ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);
        when(userRepository.save(userEntityCaptor.capture())).thenReturn(userEntity);

        // Act
        userService.saveUser(registrationDto);

        // Assert
        UserEntity capturedUserEntity = userEntityCaptor.getValue();
        assertEquals("testuser", capturedUserEntity.getUsername());
        assertEquals("testuser@example.com", capturedUserEntity.getEmail());
        assertEquals("John", capturedUserEntity.getFirstName());
        assertEquals("Doe", capturedUserEntity.getLastName());
        assertEquals("123456789", capturedUserEntity.getPhoneNumber());

        verify(userRepository).save(capturedUserEntity);
    }


    @Test
    public void findByEmail_ShouldReturnUser() {
        // Arrange
        String email = "test@example.com";
        UserEntity expectedUser = new UserEntity();
        expectedUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(expectedUser);

        // Act
        UserEntity result = userService.findByEmail(email);

        // Assert
        assertEquals(expectedUser, result);
    }

    @Test
    public void findByUsername_ShouldReturnUser() {
        // Arrange
        String username = "testuser";
        UserEntity expectedUser = new UserEntity();
        expectedUser.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(expectedUser);

        // Act
        UserEntity result = userService.findByUsername(username);

        // Assert
        assertEquals(expectedUser, result);
    }

    @Test
    public void findUserById_ShouldReturnUserDto() {
        // Arrange
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setUsername("testuser");
        userEntity.setEmail("test@example.com");

        when(userRepository.findUserById(userId)).thenReturn(userEntity);

        // Act
        UserDto result = userService.findUserById(userId);

        // Assert
        Assertions.assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("test@example.com", result.getEmail());
    }


    @Test
    public void getCurrentUser_WithAuthenticatedUser_ShouldReturnCurrentUser() {
        // Arrange
        String email = "test@example.com";
        UserEntity expectedUser = new UserEntity();
        expectedUser.setEmail(email);

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(expectedUser);

        // Act
        UserEntity result = userService.getCurrentUser();

        // Assert
        assertEquals(expectedUser, result);
    }
    @Test
    public void getCurrentUser_WithoutAuthenticatedUser_ShouldReturnNull() {
        // Arrange
        SecurityContextHolder.getContext().setAuthentication(null);

        // Act
        UserEntity result = userService.getCurrentUser();

        // Assert
        assertNull(result);
    }

}


