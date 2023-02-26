package com.project.socializer.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.socializer.security.jwt.JwtService;
import com.project.socializer.user.entity.Roles;
import com.project.socializer.user.entity.UserEntity;
import com.project.socializer.user.repository.RolesRepository;
import com.project.socializer.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.AccessDeniedException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class VerifyJwtTokenFilterTest {

    @Autowired
    VerifyJwtTokenFilter verifyJwtTokenFilter;

    @Autowired
    JwtService jwtService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    UserRepository userRepository;

    @Before("testVerifyJwtFilterIfGrantAccessToPrivateUri")
    void setUp() {
        log.warn("HEEELLLOOO");
    }

    @After("testVerifyJwtFilterIfGrantAccessToPrivateUri")
    void tearDown() {
        log.warn("GOOD BAY");
    }

    @Test
    void testVerifyJwtFilterIfSkipsPublicUri() throws Exception {
        //TEST IF THE FILTER SKIP THE PUBLIC URI'S
        mockMvc.perform(get("/api/v1/public/test"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testVerifyJwtFilterIfGrantAccessToPrivateUri() throws Exception {
        //TEST IF THE FILTER BLOCK THE PRIVATE URI'S WITHOUT THE JWT
        userRepository.deleteAll();
        rolesRepository.deleteAll();
        rolesRepository.save(new Roles("USER"));
        userRepository.save(new UserEntity("jhon","jhon","doe","jhon.doe@example.com","testpass","19-12-1999",rolesRepository.getByRoleName("USER").get()));
        userRepository.findByEmail("jhon.doe@example.com").get().setEnabled(true);

        Map<String,String> tokens = jwtService.createAccessRefreshJwtToken("jhon.doe@example.com");
        mockMvc.perform(get("/api/v1/private/test")
                        .header("Authorization","Bearer "+tokens.get("accessToken"))
                        .header("RefreshToken","Bearer "+tokens.get("refreshToken")))
                .andExpect(status().isOk());

        userRepository.deleteAll();
        rolesRepository.deleteAll();


    }
}