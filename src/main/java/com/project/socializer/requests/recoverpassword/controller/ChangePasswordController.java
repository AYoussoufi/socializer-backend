package com.project.socializer.requests.recoverpassword.controller;

import com.project.socializer.requests.recoverpassword.service.ChangePasswordService;
import com.project.socializer.requests.recoverpassword.service.RecoverPasswordService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Request;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChangePasswordController {

    @Autowired
    ChangePasswordService changePasswordService;

    @GetMapping("/api/v1/public/verify/password-recovery-token/{token}")
    public void verifyTokenRequest(@PathVariable String token, @RequestBody Map<String,String> requestBody, HttpServletResponse response) {
        //THE ACTUAL TOKEN SHOULD BE SENT IN EMAIL AS A COOKIE THAT IS STORED IN A FRONT END INSTEAD OF THE BACK END
        //LIKE THIS YOU WILL BE ABLE TO HOLD THE TOKEN AND SEND IT WHEN YOU ARE READY TO CHANGE IT WITH THE NEW PASSWORD
        String password = requestBody.get("password");
        if(password.length()<8){
            throw new RequestRejectedException("your password is too weak, please use a password with more then 8 characters");
        }
        changePasswordService.verifyToken(token,password,response);
    }
}
