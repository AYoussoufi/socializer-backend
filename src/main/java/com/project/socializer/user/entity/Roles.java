package com.project.socializer.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Entity
@Setter
@Getter
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> user;

    public Roles(String roleName){
this.roleName=roleName;
    }

    public Roles() {

    }
}
