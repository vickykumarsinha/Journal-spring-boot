package com.vickykumar.Journal.Controller;

import com.vickykumar.Journal.entity.UserEntity;
import com.vickykumar.Journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    public UserService userService;

    @GetMapping("/health-check")
    public String heathCheck(){
        return "ok";
    }


    // Creating NEW USER in Public
    @PostMapping("/create-user")
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity userEntity){
        try {
            userService.saveNewUser(userEntity);
            return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
