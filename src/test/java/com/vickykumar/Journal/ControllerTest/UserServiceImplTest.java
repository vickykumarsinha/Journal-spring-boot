package com.vickykumar.Journal.ControllerTest;


import com.vickykumar.Journal.Repository.UserRepository;
import com.vickykumar.Journal.service.UserAuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

// Mockito is used to not start whole app for testing it creates mock of actual objects to load what sneccessary

public class UserServiceImplTest {

    @InjectMocks
    private UserAuthServiceImpl userAuthService;

    @Mock   // this creates objects of other classes used in Inject Mock
    private UserRepository userRepository;

    @BeforeEach     // Before using Mockito Initialize it
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test       // test methods
    void TestAnything(){}
}
