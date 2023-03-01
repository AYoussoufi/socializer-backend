package com.project.socializer.job.delete;

import com.project.socializer.requests.verifyuseremail.entity.UserNeedVerificationEntity;
import com.project.socializer.requests.verifyuseremail.repository.UserNeedVerificationRepository;
import com.project.socializer.user.entity.Roles;
import com.project.socializer.user.entity.UserEntity;
import com.project.socializer.user.repository.RolesRepository;
import com.project.socializer.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.management.relation.Role;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Slf4j
class ExpiredUnVerifiedUsersTest {


    @Autowired
    UserRepository userRepository;
    @Autowired
    UserNeedVerificationRepository userNeedVerificationRepository;
    @Autowired
    RolesRepository rolesRepository;


    ExpiredUnVerifiedUsers expiredUnVerifiedUsers;

    UserEntity testedUser;
    UserNeedVerificationEntity testedVerifiedUserEntity;
    @BeforeEach
    void setUp() {
        rolesRepository.save(new Roles("USER"));
        testedUser = new UserEntity("test","test-f","test-l","test@test.com","testpass123","1999-10-10",rolesRepository.getByRoleName("USER").get());
        testedVerifiedUserEntity = new UserNeedVerificationEntity("test@test.com",testedUser);
        userRepository.save(testedUser);
        testedVerifiedUserEntity.setExpireDate("2010-10-10");
        userNeedVerificationRepository.save(testedVerifiedUserEntity);
        expiredUnVerifiedUsers = new ExpiredUnVerifiedUsers(userRepository,userNeedVerificationRepository);
    }

    @AfterEach
    void tearDown() {
        userNeedVerificationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void deleteExpiredData() {
        expiredUnVerifiedUsers.deleteExpiredData();
        assertTrue(userRepository.findByEmail("test@test.com").isEmpty());
        assertTrue(userNeedVerificationRepository.findByEmail("test@test.com").isEmpty());
    }
}