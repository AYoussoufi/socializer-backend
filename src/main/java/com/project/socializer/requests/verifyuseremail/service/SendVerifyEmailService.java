package com.project.socializer.requests.verifyuseremail.service;

import com.project.socializer.mailsender.MailSenderService;
import com.project.socializer.requests.login.exception.UserEmailNotFoundException;
import com.project.socializer.requests.verifyuseremail.entity.UserNeedVerificationEntity;
import com.project.socializer.requests.verifyuseremail.repository.UserNeedVerificationRepository;
import com.project.socializer.user.repository.UserRepository;
import com.project.socializer.util.responsewriter.ResponseBody;
import com.project.socializer.util.responsewriter.ResponseWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendVerifyEmailService {

    UserNeedVerificationRepository userNeedVerificationRepository;

    MailSenderService mailSenderService;
    ResponseWriter responseWriter;

    UserRepository userRepository;
    @Autowired
    public SendVerifyEmailService(UserNeedVerificationRepository userNeedVerificationRepository, MailSenderService mailSenderService, UserRepository userRepository, ResponseWriter responseWriter) {
        this.userNeedVerificationRepository = userNeedVerificationRepository;
        this.mailSenderService = mailSenderService;
        this.userRepository = userRepository;
        this.responseWriter = responseWriter;
    }

    public void saveUser(String email, HttpServletResponse response) {
        Boolean exist = userRepository.findByEmail(email).isPresent();
        UserNeedVerificationEntity user = userNeedVerificationRepository.findByEmail(email).get();
        if(exist){
            user.setCodePin(user.generateRandomPin());
            userNeedVerificationRepository.save(user);
            sendMail(email);
            responseWriter.writeJsonResponse(response,new ResponseBody(response.getStatus(), "user email verification pin has successfully sent to email","/api/v1/public/send/mail/verification"));
        }else{
            throw new UserEmailNotFoundException("you are trying to verify a user that doesn't exist");
        }
    }

    private void sendMail(String email) {
        mailSenderService.sendEmailToVerifyEmail(email,"Welcome to SOCIALIZER, Please we want you to verify your email !",userNeedVerificationRepository.findByEmail(email).get().getCodePin());
    }


}
