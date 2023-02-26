package com.project.socializer.user.repository;

import com.project.socializer.user.entity.Roles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class RolesRepositoryTest {

    @Autowired
    RolesRepository rolesRepository;

    @BeforeEach
    void setUp() {
        rolesRepository.save(new Roles("USER"));
    }

    @AfterEach
    void afterEach() {
        rolesRepository.deleteAll();
    }

    @Test
    void getByRoleName() {
        Optional<Roles> role = rolesRepository.getByRoleName("USER");
        assertTrue(rolesRepository.getByRoleName("USER").isPresent(),"The Role Exist");
    }

    @Test
    void getByRoleNameDosentExist() {
        Optional<Roles> role = rolesRepository.getByRoleName("NOTFOUND");
        assertFalse(rolesRepository.getByRoleName("NOTFOUND").isPresent(),"The Role Doesn't Exist");
    }

}