package com.project.socializer.requests.registration.services;

import com.project.socializer.requests.registration.repository.RegistrationRepository;
import com.project.socializer.requests.registration.request.SignUpRequest;
import com.project.socializer.user.entity.UserEntity;
import com.project.socializer.user.repository.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegistrationService {
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(SignUpRequest signUpRequest){
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
      registrationRepository.save(new UserEntity(
              signUpRequest.getFirstName(),
              signUpRequest.getLastName(),
              signUpRequest.getEmail(),
              signUpRequest.getPassword(),
              signUpRequest.getBirthDay(),
            rolesRepository.getByRoleName("USER").get()));
    }

}
