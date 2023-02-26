package com.project.socializer.requests.verifyuseremail.service;

import com.project.socializer.requests.verifyuseremail.entity.UserNeedVerificationEntity;
import com.project.socializer.requests.verifyuseremail.repository.UserNeedVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyUserEmailService {

    private UserNeedVerificationRepository userNeedVerificationRepository;
    private UserNeedVerificationEntity userNeedVerificationEntity;



    @Autowired
    public VerifyUserEmailService(UserNeedVerificationRepository userNeedVerificationRepository) {
        this.userNeedVerificationRepository = userNeedVerificationRepository;
    }

}
