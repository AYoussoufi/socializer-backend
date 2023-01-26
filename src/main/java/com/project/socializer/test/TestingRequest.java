package com.project.socializer.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestingRequest {

    @RequestMapping("test")
    public String test(){
        return "<h1>Yes Request is working</h1>";
    }
}
