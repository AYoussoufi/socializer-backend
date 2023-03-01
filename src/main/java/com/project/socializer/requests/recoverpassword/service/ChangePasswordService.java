package com.project.socializer.requests.recoverpassword.service;

import com.project.socializer.requests.recoverpassword.entity.UserRecoverPasswordEntity;
import com.project.socializer.requests.recoverpassword.exception.InvalidToken;
import com.project.socializer.requests.recoverpassword.repository.UserRecoverPasswordRepository;
import com.project.socializer.user.entity.UserEntity;
import com.project.socializer.user.repository.UserRepository;
import com.project.socializer.util.responsewriter.ResponseBody;
import com.project.socializer.util.responsewriter.ResponseWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordService {

    UserRecoverPasswordRepository userRecoverPasswordRepository;
    ResponseWriter responseWriter;

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Autowired

    public ChangePasswordService(UserRecoverPasswordRepository userRecoverPasswordRepository, ResponseWriter responseWriter,UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRecoverPasswordRepository = userRecoverPasswordRepository;
        this.responseWriter = responseWriter;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void verifyToken(String token, String password, HttpServletResponse response){
       if(userRecoverPasswordRepository.findUserRecoverPasswordEntityByToken(token).isEmpty()){
           throw new InvalidToken("token you provided is invalid, are you lost ?");
       }
        UserRecoverPasswordEntity userRecoverPasswordEntity = userRecoverPasswordRepository.findUserRecoverPasswordEntityByToken(token).get();
        UserEntity user = userRepository.findByEmail(userRecoverPasswordEntity.getEmail()).get();
       user.setPassword(passwordEncoder.encode(password));
       userRepository.save(user);
       userRecoverPasswordRepository.delete(userRecoverPasswordEntity);
       responseWriter.writeJsonResponse(response,new ResponseBody(200,"we've successfully changed your password you can now access your account with the new password.","/api/v1/public/verify/password-recovery-token/"+token));
    }
}
