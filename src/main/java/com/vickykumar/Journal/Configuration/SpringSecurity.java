package com.vickykumar.Journal.Configuration;

import com.vickykumar.Journal.service.UserAuthServiceImpl;
import com.vickykumar.Journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// By default, we added dependency of Spring security
// it allows to customize it we need @EnableWebSecurity
// SecurityFilterChain- to configure spring secutiry
@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserAuthServiceImpl userAuthService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF for /public/** endpoints
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/public/**", "/user/**", "/journal/**", "/admin/**"));

        // Configure endpoint access
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/public/**").permitAll() // Allow unauthenticated access
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/journal/**").authenticated()
                .anyRequest().permitAll() // Default to permit all
        );

        // Enable basic HTTP authentication
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
    }

    @Bean   // Encode Password to store in db
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
