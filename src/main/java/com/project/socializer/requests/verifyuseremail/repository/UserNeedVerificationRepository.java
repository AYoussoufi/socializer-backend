package com.project.socializer.requests.verifyuseremail.repository;

import com.project.socializer.requests.verifyuseremail.entity.UserNeedVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserNeedVerificationRepository extends JpaRepository<UserNeedVerificationEntity, Long> {
    Optional<UserNeedVerificationEntity> findByEmail(String email);
    void deleteByEmail(String email);
}
