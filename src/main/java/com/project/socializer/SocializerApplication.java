package com.project.socializer;

import com.project.socializer.test.InitService;
import com.project.socializer.user.entity.Hobby;
import com.project.socializer.user.entity.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class SocializerApplication implements CommandLineRunner {

	@Autowired
	InitService initService;


	public static void main(String[] args) {
		SpringApplication.run(SocializerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initService.saveRoles(new Roles("ADMIN"));
		initService.saveRoles(new Roles("USER"));
		/////////////////////////////////////////////////////////////////////
		initService.saveHobby(new Hobby("Reading"));
		initService.saveHobby(new Hobby("Martial Arts"));
		initService.saveHobby(new Hobby("Video Games"));
		initService.saveHobby(new Hobby("Yoga"));
		initService.saveHobby(new Hobby("Traveling"));
		initService.saveHobby(new Hobby("Golf"));
		initService.saveHobby(new Hobby("Writing"));
		initService.saveHobby(new Hobby("Running"));
		initService.saveHobby(new Hobby("Tennis"));
		initService.saveHobby(new Hobby("Baseball"));
		initService.saveHobby(new Hobby("BasketBall"));
		initService.saveHobby(new Hobby("FootBall"));
		initService.saveHobby(new Hobby("Volunteer Work"));
		initService.saveHobby(new Hobby("Dancing"));
		initService.saveHobby(new Hobby("Painting"));
		initService.saveHobby(new Hobby("Cooking"));
		initService.saveHobby(new Hobby("Movie Watching"));
		initService.saveHobby(new Hobby("Podcasts"));

	}
}
