package com.project.socializer.requests.recoverpassword.service;

import com.project.socializer.mailsender.MailSenderService;
import com.project.socializer.requests.login.exception.UserEmailNotFoundException;
import com.project.socializer.requests.recoverpassword.entity.UserRecoverPasswordEntity;
import com.project.socializer.requests.recoverpassword.repository.UserRecoverPasswordRepository;
import com.project.socializer.user.repository.UserRepository;
import com.project.socializer.util.responsewriter.ResponseBody;
import com.project.socializer.util.responsewriter.ResponseWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RecoverPasswordService {

    UserRepository userRepository;
    MailSenderService mailSenderService;
    ResponseWriter responseWriter;

    UserRecoverPasswordRepository userRecoverPasswordRepository;

    @Autowired
    public RecoverPasswordService(UserRepository userRepository, MailSenderService mailSenderService,ResponseWriter responseWriter,UserRecoverPasswordRepository userRecoverPasswordRepository) {
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
        this.responseWriter = responseWriter;
        this.userRecoverPasswordRepository = userRecoverPasswordRepository;
    }

    public void recoverPassword(String email, HttpServletResponse response) {
        if(userRepository.findByEmail(email).isEmpty()){
            throw new UserEmailNotFoundException("the email you are trying to recover it's password, doesn't exist.");
        }
        String token = UUID.randomUUID().toString();
        userRecoverPasswordRepository.save(new UserRecoverPasswordEntity(email,token));
        mailSenderService.sendEmailToVerifyPasswordRecovery(email,"Socializer At Your Service ! Recover your password now","localhost:8080//api/v1/public/verify/password-recovery-token/"+token);
        responseWriter.writeJsonResponse(response,new ResponseBody(200,"we've sent an email to verify if you are the legit user","/api/v1/public/recover-password"));
    }
}
