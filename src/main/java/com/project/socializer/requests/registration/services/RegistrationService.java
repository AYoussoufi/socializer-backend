package com.project.socializer.requests.registration.services;

import com.project.socializer.requests.registration.exception.UserExistException;
import com.project.socializer.requests.registration.requestBody.SignUpRequest;
import com.project.socializer.requests.verifyuseremail.entity.UserNeedVerificationEntity;
import com.project.socializer.requests.verifyuseremail.repository.UserNeedVerificationRepository;
import com.project.socializer.user.entity.Roles;
import com.project.socializer.user.entity.UserEntity;
import com.project.socializer.user.repository.RolesRepository;
import com.project.socializer.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegistrationService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserNeedVerificationRepository userNeedVerificationRepository;

    @Autowired
    public RegistrationService(UserRepository userRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder,UserNeedVerificationRepository userNeedVerificationRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.userNeedVerificationRepository = userNeedVerificationRepository;
    }

    public void saveUser(SignUpRequest signUpRequest) throws Exception {
        if(rolesRepository.getByRoleName("USER").isEmpty()){
            rolesRepository.save(new Roles("USER"));
        }
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        UserEntity userEntity = new UserEntity(
                signUpRequest.getPseudo(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getBirthDay(),
                rolesRepository.getByRoleName("USER").get());
        try{
            userRepository.save(userEntity);
            userNeedVerificationRepository.save(new UserNeedVerificationEntity(signUpRequest.getEmail(),userRepository.findByEmail(signUpRequest.getEmail()).get()));
        }catch (Exception e){
            throw new UserExistException("This user is already exist, did you forget your password ?");
        }
    }
}
