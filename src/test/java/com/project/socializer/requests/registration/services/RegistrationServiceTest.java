package com.project.socializer.requests.registration.services;

import com.project.socializer.requests.registration.exception.UserExistException;
import com.project.socializer.requests.registration.request.SignUpRequest;
import com.project.socializer.user.entity.Roles;
import com.project.socializer.user.entity.UserEntity;
import com.project.socializer.user.repository.RolesRepository;
import com.project.socializer.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    private RegistrationService registrationService;

    @BeforeEach
    void SetUp(){
        this.registrationService = new RegistrationService(userRepository,rolesRepository,passwordEncoder);
    }

    @Test
    public void saveUser() throws Exception {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest("TestingMan",
                "First", "Last", "first.last@example.com",
                "password", "2000-1-1");
        Roles role = new Roles("USER");
        when(rolesRepository.getByRoleName("USER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        // Act
        assertDoesNotThrow(()->{registrationService.saveUser(signUpRequest);});
        // Assert
        ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(argumentCaptor.capture());
        UserEntity savedUser = argumentCaptor.getValue();
        assertEquals("TestingMan", savedUser.getPseudo());
        assertEquals("First", savedUser.getFirstName());
        assertEquals("Last", savedUser.getLastName());
        assertEquals("first.last@example.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("2000-1-1", savedUser.getBirthDay());
        assertEquals(role, savedUser.getRoles().iterator().next());
    }

    @Test
    public void saveUserThrowUserExist() {
        // Arrange
        when(rolesRepository.getByRoleName("USER")).thenReturn(Optional.of(new Roles()));
        doThrow(UserExistException.class).when(userRepository).save(any(UserEntity.class));

        // Act and assert
        SignUpRequest signUpRequest = new SignUpRequest();
        Throwable exception = assertThrows(UserExistException.class, () -> registrationService.saveUser(signUpRequest));
        assertEquals("This user is already exist, did you forget your password ?", exception.getMessage());
    }

}