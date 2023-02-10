package com.project.socializer.runner;

import com.project.socializer.user.entity.Hobby;
import com.project.socializer.user.entity.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitService.class);
    @Autowired
    private InitHobby initHobby;
    @Autowired
    private InitRoles initRoles;

    public void saveHobby(Hobby hobby){
        initHobby.save(hobby);
    }
    public void saveRoles(Roles roles){
        initRoles.save(roles);
    }

}
