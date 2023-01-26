package com.project.socializer.test;

import com.project.socializer.user.entity.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InitHobby extends JpaRepository<Hobby, Long> {
}
