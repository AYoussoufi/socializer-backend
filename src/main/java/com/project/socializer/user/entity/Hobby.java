package com.project.socializer.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true, length = 45)
    private String name;

    @ManyToMany(mappedBy = "hobbies")
    private Set<UserEntity> user;

    public Hobby(String name) {
        this.name = name;
    }

    public Hobby() {

    }
}
