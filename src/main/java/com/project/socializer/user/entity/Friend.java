package com.project.socializer.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Friend {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String hashTag;
    @ManyToMany(mappedBy="friends")
    private Set<UserEntity> userEntity;

}
