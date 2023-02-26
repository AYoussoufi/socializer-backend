package com.project.socializer.requests.verifyuseremail.service;

import com.project.socializer.mailsender.MailSenderService;
import com.project.socializer.requests.login.exception.UserEmailNotFoundException;
import com.project.socializer.requests.verifyuseremail.entity.UserNeedVerificationEntity;
import com.project.socializer.requests.verifyuseremail.repository.UserNeedVerificationRepository;
import com.project.socializer.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class SaveAndSendEmailService {

    @Autowired
    UserNeedVerificationRepository userNeedVerificationRepository;
    @Autowired
    MailSenderService mailSenderService;
    @Autowired
    UserRepository userRepository;
    public void saveUser(String email) {
        Integer pin = (int) Math.floor(Math.random()*10000);
        LocalDate currentDate = LocalDate.now().plusDays(1);
        String expireDate = DateTimeFormatter.ofPattern("yyyy--MM--dd").format(currentDate);

        if(userRepository.findByEmail(email).isPresent()){
            if(userNeedVerificationRepository.findByEmail(email).isEmpty()){
                userNeedVerificationRepository.save(new UserNeedVerificationEntity(email,pin,expireDate,0));
            }else {
                userNeedVerificationRepository.deleteById(userNeedVerificationRepository.findByEmail(email).get().getId());
                userNeedVerificationRepository.save(new UserNeedVerificationEntity(email,pin,expireDate,0));
            }
        }else{
            throw new UserEmailNotFoundException("you are trying to verify a user that doesn't exist");
        }
    }

    public void sendMail(String email) throws MessagingException {
        mailSenderService.sendEmail(email,"Welcome to SOCIALIZER, Please we want you to verify your email !",userNeedVerificationRepository.findByEmail(email).get().getCodePin());
    }
}
