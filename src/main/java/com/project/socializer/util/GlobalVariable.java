package com.project.socializer.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GlobalVariable implements CommandLineRunner {
    public static ArrayList<String> publicUri = new ArrayList<>();
    @Override
    public void run(String... args) throws Exception {
        publicUri.add("/api/v1/public/auth/signup");
        publicUri.add("/api/v1/public/auth/login");
        publicUri.add("/api/v1/public/auth/refresh/jwt");
    }
}
