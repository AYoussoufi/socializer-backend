package com.project.socializer.requests.zpublicUrlTest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicUrlController {

    @RequestMapping("/api/v1/public/test")
    public String publicUrl(){
        return "public access is granted";
    }

    @RequestMapping("/api/v1/private/test")
    public String privateUrl(){
        return "private access is granted";
    }
}
