package com.project.socializer.test;

import com.project.socializer.user.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InitRoles extends JpaRepository<Roles, Long> {
}
