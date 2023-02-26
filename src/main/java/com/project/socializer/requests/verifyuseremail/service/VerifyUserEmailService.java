package com.project.socializer.requests.verifyuseremail.service;

import com.project.socializer.requests.verifyuseremail.entity.VerifyUserEmailEntity;
import com.project.socializer.requests.verifyuseremail.repository.VerifyUserEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyUserEmailService {

    private VerifyUserEmailRepository verifyUserEmailRepository;
    private VerifyUserEmailEntity verifyUserEmailEntity;



    @Autowired
    public VerifyUserEmailService(VerifyUserEmailRepository verifyUserEmailRepository) {
        this.verifyUserEmailRepository = verifyUserEmailRepository;
    }

}
