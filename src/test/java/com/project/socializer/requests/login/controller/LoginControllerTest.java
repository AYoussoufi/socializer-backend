package com.project.socializer.requests.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.socializer.requests.login.request.LoginRequest;
import com.project.socializer.requests.registration.request.SignUpRequest;
import com.project.socializer.user.entity.Roles;
import com.project.socializer.user.entity.UserEntity;
import com.project.socializer.user.repository.RolesRepository;
import com.project.socializer.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;

    private UserEntity user;


    @Autowired
    PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        if(userRepository.findByEmail("test@gmail.com").isEmpty()){
            rolesRepository.save(new Roles("USER"));
            user = new UserEntity("test","FirstName","LastName","test@gmail.com",passwordEncoder.encode("test123"),"1999-12-19",rolesRepository.getByRoleName("USER").get());
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

  @AfterEach
  public void afterEach(){
      userRepository.deleteAll();
        rolesRepository.deleteAll();

  }

    @Test
    public void testLogInWithLegitInputs() throws Exception {
        LoginRequest loginRequest = new LoginRequest("test@gmail.com","test123");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/public/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").hasJsonPath())
                .andExpect(jsonPath("$.accessToken").value(matchesPattern(".{442,}")))
                .andExpect(jsonPath("$.refreshToken").hasJsonPath())
                .andExpect(jsonPath("$.refreshToken").value(matchesPattern(".{442,}")))
                .andReturn();
    }

    @Test
    public void testLogInWithNotLegitInputs() throws Exception {
        LoginRequest loginRequest = new LoginRequest("doesnotExist@gmail.com","test123");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/public/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.exception").hasJsonPath())
                .andExpect(jsonPath("$.exception").value("BadCredentialsException"))
                .andReturn();

    }
}