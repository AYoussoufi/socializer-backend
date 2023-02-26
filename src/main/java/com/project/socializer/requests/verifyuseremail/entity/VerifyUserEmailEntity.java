package com.project.socializer.requests.verifyuseremail.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VerifyUserEmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false,unique = true)
    private Integer codePin;
    @Column(nullable = false,unique = true)
    private String expireDate;
    @Column(nullable = false,unique = true)
    private Integer attempt;

    public VerifyUserEmailEntity(String email, Integer codePin, String expireDate, Integer attempt) {
        this.email = email;
        this.codePin = codePin;
        this.expireDate = expireDate;
        this.attempt = attempt;
    }
}
