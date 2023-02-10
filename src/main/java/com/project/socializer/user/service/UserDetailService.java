package com.project.socializer.user.service;

        import com.project.socializer.requests.login.exception.UserEmailNotFoundException;
        import com.project.socializer.user.repository.UserRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.authentication.BadCredentialsException;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.stereotype.Service;

        import java.util.NoSuchElementException;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        try{
            return userRepository.findByEmail(username).get();
        }catch (NoSuchElementException e ){
            throw new UserEmailNotFoundException("email not found, please signup before trying to login.");
        }
    }
}

