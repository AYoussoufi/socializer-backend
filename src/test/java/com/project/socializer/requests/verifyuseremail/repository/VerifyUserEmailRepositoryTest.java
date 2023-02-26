package com.project.socializer.requests.verifyuseremail.repository;

import com.project.socializer.requests.verifyuseremail.entity.VerifyUserEmailEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VerifyUserEmailRepositoryTest {

    @Autowired
    private VerifyUserEmailRepository verifyUserEmailRepository;


    @BeforeEach
    void setUp() {
        verifyUserEmailRepository.save(new VerifyUserEmailEntity("jhon.doe@example.com",4000,"1999",0));
    }

    @AfterEach
    void tearDown() {
        verifyUserEmailRepository.deleteAll();
    }

    @Test
    void findByEmail() {
        assertTrue(verifyUserEmailRepository.findByEmail("jhon.doe@example.com").isPresent());
    }

    @Test
    void findByEmailIfDosentExist() {
        assertFalse(verifyUserEmailRepository.findByEmail("dosentExist@example.com").isPresent());
    }
}