package com.project.socializer.user.repository;

import com.project.socializer.SocializerApplication;
import com.project.socializer.user.entity.Roles;
import com.project.socializer.user.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RolesRepository rolesRepository;


    @BeforeEach
    void setUp() {
        rolesRepository.save(new Roles("USER"));
        userRepository.save(new UserEntity("Jhon","Doe","jhon.doe@gmail.com","test1234","1999-12-19",rolesRepository.getByRoleName("USER").get()));
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    void findByEmail() {
        Optional<UserEntity> userTest = userRepository.findByEmail("jhon.doe@gmail.com");
        assertTrue(userTest.isPresent(),"User Exist");
    }

    @Test
    void dontFindByEmail(){
        Optional<UserEntity> userTest = userRepository.findByEmail("notfound@gmail.com");
        assertFalse(userTest.isPresent(),"User Doesn't Exist");
    }
}