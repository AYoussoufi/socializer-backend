package com.project.socializer.requests.registration.services;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    public void saveUser_ShouldSaveEncodedPassword() {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest(
                "First", "Last", "first.last@example.com",
                "password", "2000-1-1");
        Roles role = new Roles("USER");
        when(rolesRepository.getByRoleName("USER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Act
        registrationService.saveUser(signUpRequest);

        // Assert
        ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(argumentCaptor.capture());
        UserEntity savedUser = argumentCaptor.getValue();
        assertEquals("First", savedUser.getFirstName());
        assertEquals("Last", savedUser.getLastName());
        assertEquals("first.last@example.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("2000-1-1", savedUser.getBirthDay());
        assertEquals(role, savedUser.getRoles().iterator().next());
    }
}