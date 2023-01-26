package com.project.socializer.requests.registration.repository;

import com.project.socializer.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<UserEntity,Long> {
}
