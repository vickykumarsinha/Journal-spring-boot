package com.vickykumar.Journal.service;


import com.vickykumar.Journal.Repository.UserRepository;
import com.vickykumar.Journal.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    public UserRepository userRepository;

    private static final PasswordEncoder passwordEncode = new BCryptPasswordEncoder();

    public void saveUser(UserEntity userEntity){
        userRepository.save(userEntity);
    }
    public void saveNewUser(UserEntity userEntity){

        // Encode pass then save it
        userEntity.setPassword(passwordEncode.encode(userEntity.getPassword()));
        userEntity.setRoles(List.of("USER"));
        userRepository.save(userEntity);
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<UserEntity> findUserById(ObjectId id){
        return userRepository.findById(id);
    }
    public UserEntity findUserByName(String userName){
        return userRepository.findByUsername(userName);
    }

    public void deleteUser(ObjectId id){
        userRepository.deleteById(id);
    }

}
