package com.vickykumar.Journal.service;

import com.vickykumar.Journal.Repository.UserRepository;
import com.vickykumar.Journal.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class UserAuthServiceImpl implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Finds user
        UserEntity user = userRepository.findByUsername(username);

        // Converts the UserEntity into a UserDetails object,
        // which Spring Security uses for authentication and authorization
        if(user != null){

            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User Not Found with username: " + username);
    }
}
