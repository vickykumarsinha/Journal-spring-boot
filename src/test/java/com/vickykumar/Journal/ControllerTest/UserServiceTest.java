package com.vickykumar.Journal.ControllerTest;

import com.vickykumar.Journal.Repository.UserRepository;
import com.vickykumar.Journal.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    public UserRepository userRepository;

    @Test
    public void userTesting(){

    }

}
