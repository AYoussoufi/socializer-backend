package com.project.socializer.requests.verifyuseremail.entity;

import com.project.socializer.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserNeedVerificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private Integer codePin;
    @Column(nullable = false)
    private String expireDate;
    @Column(nullable = false)
    private Integer attempt = 5;



    @OneToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    public UserNeedVerificationEntity(String email,UserEntity userEntity) {
        this.email = email;
        this.userEntity = userEntity;
        this.codePin = this.generateRandomPin();
        this.expireDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now().plusDays(3));
    }

    public Integer generateRandomPin(){
        Integer pin = (int) Math.floor(Math.random()*10000);
        if(pin<1000){
            String tempoPinString = pin.toString();
            int pinLength = tempoPinString.length();
            int neededNumber = pinLength - 4;
            while(neededNumber>0){
                tempoPinString = 0+tempoPinString;
                neededNumber--;
            }
            return Integer.parseInt(tempoPinString);
        }
        return pin;
    }
}
