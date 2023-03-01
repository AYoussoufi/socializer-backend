package com.project.socializer.requests.verifyuseremail.repository;

import com.project.socializer.requests.verifyuseremail.entity.UserNeedVerificationEntity;
import com.project.socializer.user.entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VerifyUserEmailRepositoryTest {

    @Autowired
    private UserNeedVerificationRepository userNeedVerificationRepository;


    @BeforeEach
    void setUp() {
        userNeedVerificationRepository.save(new UserNeedVerificationEntity("jhon.doe@example.com",new UserEntity()));
    }

    @AfterEach
    void tearDown() {
        userNeedVerificationRepository.deleteAll();
    }

    @Test
    void findByEmail() {
        assertTrue(userNeedVerificationRepository.findByEmail("jhon.doe@example.com").isPresent());
    }

    @Test
    void findByEmailIfDosentExist() {
        assertFalse(userNeedVerificationRepository.findByEmail("dosentExist@example.com").isPresent());
    }
}