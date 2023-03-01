package com.project.socializer.requests.recoverpassword.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
public class UserRecoverPasswordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false,unique = false)
    private String expiredDate;

    public UserRecoverPasswordEntity(String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.expiredDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now().plusDays(3));
    }
}
