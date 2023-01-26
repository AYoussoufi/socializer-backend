package com.project.socializer.security;




import com.project.socializer.security.config.JwtConfigurer;
import com.project.socializer.security.jwt.JwtService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;



@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /*private final LoginService userService;
    PasswordEncoder passwordEncoder;*/
    private final JwtService jwtService;

    @Autowired
    public SecurityConfig(/*LoginService userService, PasswordEncoder passwordEncoder*/JwtService jwtService) {
        /*this.userService = userService;
        this.passwordEncoder = passwordEncoder;*/
        this.jwtService = jwtService;
    }

    @Bean
    public DefaultSecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/signup")
                .permitAll()
                .requestMatchers("/api/v1/auth/login")
                .permitAll()
                .requestMatchers("/test")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(new JwtConfigurer(jwtService))
                .and()
                .formLogin().disable()
                .build();
    }

    /*@Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }*/
}
