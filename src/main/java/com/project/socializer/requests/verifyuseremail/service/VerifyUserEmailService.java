package com.project.socializer.requests.verifyuseremail.service;

import com.project.socializer.requests.login.exception.UserEmailNotFoundException;
import com.project.socializer.requests.verifyuseremail.entity.UserNeedVerificationEntity;
import com.project.socializer.requests.verifyuseremail.exception.BadPin;
import com.project.socializer.requests.verifyuseremail.exception.TooMuchAttempt;
import com.project.socializer.requests.verifyuseremail.repository.UserNeedVerificationRepository;
import com.project.socializer.user.entity.UserEntity;
import com.project.socializer.user.repository.UserRepository;
import com.project.socializer.user.service.UserDetailService;
import com.project.socializer.util.responsewriter.ResponseBody;
import com.project.socializer.util.responsewriter.ResponseWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VerifyUserEmailService {

    private UserNeedVerificationRepository userNeedVerificationRepository;
    private UserRepository userRepository;
    private UserEntity realAcc;

    private UserDetailService userDetailService;

    private UserNeedVerificationEntity needVerifyAcc;

    private ResponseWriter responseWriter;

    @Autowired
    public VerifyUserEmailService(UserNeedVerificationRepository userNeedVerificationRepository, UserRepository userRepository, ResponseWriter responseWriter,UserDetailService userDetailService) {
        this.userNeedVerificationRepository = userNeedVerificationRepository;
        this.userRepository = userRepository;
        this.responseWriter = responseWriter;
        this.userDetailService = userDetailService;
    }


    public void verifyUserEmail(Integer pin,String email,HttpServletResponse response){
        this.getUserNeedVerification(email);
        this.getRealUser(email);
        this.getVerifyAcc(email);
        this.verifyPin(pin,response);
    }

    private boolean getUserNeedVerification(String email){
        if(userNeedVerificationRepository.findByEmail(email).isPresent() && userRepository.findByEmail(email).isPresent()){
            return true;
        }
        throw  new UserEmailNotFoundException("this user you trying to verify doesn't exist, please signup before accessing the platform");
    }

    private UserEntity getRealUser(String email){
        if(realAcc == null){
            realAcc = userRepository.findByEmail(email).get();
        }
        return realAcc;
    }

    private UserNeedVerificationEntity getVerifyAcc(String email){
        if(needVerifyAcc == null){
            needVerifyAcc = userNeedVerificationRepository.findByEmail(email).get();
        }
        return needVerifyAcc;
    }

    private void verifyPin(Integer pin, HttpServletResponse response){
        Integer actualPin = needVerifyAcc.getCodePin();
        if(pin.equals(actualPin)){
            this.realAcc.setEnabled(true);
            userRepository.save(this.realAcc);
            if(this.realAcc.isEnabled()){
                userNeedVerificationRepository.deleteById(needVerifyAcc.getId());
                responseWriter.writeJsonResponse(response,new ResponseBody(response.getStatus(), "congratulations, your account is now enabled you can access the platform now","/api/v1/public/verifying-email"));
            }
        }else {
            handleAttempt();
        }
    }

    private void handleAttempt(){
        needVerifyAcc.setAttempt(needVerifyAcc.getAttempt() - 1);
        userNeedVerificationRepository.save(this.needVerifyAcc);
        Integer attempts = needVerifyAcc.getAttempt();
        if(attempts<=0){
            userNeedVerificationRepository.deleteById(needVerifyAcc.getId());
            userRepository.deleteById(realAcc.getId());
            throw new TooMuchAttempt("your account is deleted because of too much bad pin attempts, re-signup with a valid email address");
        }
        throw new BadPin("pin you used doesn't match the pin we sent in your email, please make sure you are using the right pin or re-send a new one you still have "+attempts+" attempts left, before your account gets deleted automatically");
    }

}
