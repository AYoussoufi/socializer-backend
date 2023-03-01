package com.project.socializer.requests.recoverpassword.repository;

import com.project.socializer.requests.recoverpassword.entity.UserRecoverPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRecoverPasswordRepository extends JpaRepository<UserRecoverPasswordEntity, Long> {

    Optional<UserRecoverPasswordEntity> findUserRecoverPasswordEntityByToken(String token);
}
