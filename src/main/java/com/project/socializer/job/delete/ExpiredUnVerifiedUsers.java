package com.project.socializer.job.delete;

import com.project.socializer.requests.verifyuseremail.entity.UserNeedVerificationEntity;
import com.project.socializer.requests.verifyuseremail.repository.UserNeedVerificationRepository;
import com.project.socializer.user.entity.UserEntity;
import com.project.socializer.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExpiredUnVerifiedUsers {

    UserRepository userRepository;

    UserNeedVerificationRepository userNeedVerificationRepository;
    @Autowired
    public ExpiredUnVerifiedUsers(UserRepository userRepository, UserNeedVerificationRepository userNeedVerificationRepository) {
        this.userRepository = userRepository;
        this.userNeedVerificationRepository = userNeedVerificationRepository;
    }



    @Scheduled(cron = "0 0 0 * * *") // run daily at midnight
    public void deleteExpiredData() {
        String today = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
        List<UserNeedVerificationEntity> expiredUsers = userNeedVerificationRepository.findUserNeedVerificationEntitiesByExpireDateBefore(today);
        List<String> expiredEmails = new ArrayList<>();
        expiredUsers.forEach(expiredUser ->{
            expiredEmails.add(expiredUser.getEmail());
        });
        List<UserEntity> expiredUserEntities = userRepository.findByEmailIn(expiredEmails);

        userNeedVerificationRepository.deleteAll(expiredUsers);
        userRepository.deleteAll(expiredUserEntities);
    }


}
