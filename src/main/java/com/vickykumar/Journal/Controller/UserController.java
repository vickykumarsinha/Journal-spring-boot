package com.vickykumar.Journal.Controller;

import com.vickykumar.Journal.Repository.UserRepository;
import com.vickykumar.Journal.entity.UserEntity;
import com.vickykumar.Journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


//    CreateUser method in public controller


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity){

        // take auth from login pass
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        // update acc
        UserEntity userInDB = userService.findUserByName(userName);
        if(userInDB != null){
            userInDB.setUsername(userEntity.getUsername());
            userInDB.setPassword(userEntity.getPassword());
            userService.saveNewUser(userInDB);
            return new ResponseEntity<>(userInDB,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        userRepository.deleteByUsername(auth.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //
//    @GetMapping("/id/{id}")
//    public ResponseEntity<UserEntity> getUserById(@PathVariable ObjectId id){
//
//        Optional<UserEntity> userEntity = userService.findUserById(id);
//
//        if(userEntity.isPresent()){
//            return new ResponseEntity<>(userEntity.get(), HttpStatus.FOUND);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }



}
