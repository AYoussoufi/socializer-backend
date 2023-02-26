package com.project.socializer.requests.verifyuseremail.repository;

import com.project.socializer.requests.verifyuseremail.entity.VerifyUserEmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifyUserEmailRepository extends JpaRepository<VerifyUserEmailEntity, Long> {
    Optional<VerifyUserEmailEntity> findByEmail(String username);
}
