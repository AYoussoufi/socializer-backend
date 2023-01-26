package com.project.socializer.user.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Entity
@Setter
@Getter
@Slf4j
public class UserEntity implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false, length = 45)
    private String firstName;

    @Column(nullable = false, unique = false, length = 45)
    private String lastName;

    @Column(nullable = false, unique = false, length = 45)
    private String birthDay;

    @Column(nullable = false, unique = true, length = 85)
    private String email;

    @Column(nullable = false, unique = false)
    private String password;

    @Column(nullable = true,unique = false, length = 200)
    private String about;

    @Column(nullable = true,unique = false,length = 60)
    private String indentity;

    @Column(nullable = false,unique = false)
    private String hashTag = "#????";

    @Column(nullable = true,unique = false)
    private String imageFileName = "defaultImage";

    @ManyToMany
    private Set<Friend> friends = new HashSet<Friend>();
    @ManyToMany
    private Set<Hobby> hobbies= new HashSet<Hobby>();
    @ManyToMany
    private Set<Roles> roles = new HashSet<Roles>();

    public UserEntity() {
    }

    public UserEntity(String firstName,String lastName,String email,String password,String birthDay,Roles roles) {
        log.warn(roles.toString());
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDay = birthDay;
        this.roles.add(roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
