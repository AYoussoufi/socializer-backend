package com.project.socializer.user.service;

import com.project.socializer.requests.login.exception.UserEmailNotFoundException;
import com.project.socializer.user.entity.UserEntity;
import com.project.socializer.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserDetailServiceTest {

    @Mock
    private UserRepository userRepository;
    @Autowired
    UserDetailService userDetailService;

    @Test
    void loadUserByUsername() {
        //When
        when(userRepository.findByEmail("Jhon.Doe@gmail.com")).thenReturn(Optional.of(new UserEntity()));
        //Execute
        userRepository.findByEmail("Jhon.Doe@gmail.com");
        //Verify
        verify(userRepository).findByEmail("Jhon.Doe@gmail.com");
        //Assert
        assertDoesNotThrow(()->{
            userRepository.findByEmail("Jhon.Doe@gmail.com");
        });
    }

    @Test
    void loadUserByUsernameThatIsNotFound() {
        //When
        doThrow(UserEmailNotFoundException.class).when(userRepository).findByEmail("User.NotFound@gmail.com");
        //Execute And Verify
        assertThrows(UserEmailNotFoundException.class,()->{
            userRepository.findByEmail("User.NotFound@gmail.com");
        });
    }
}