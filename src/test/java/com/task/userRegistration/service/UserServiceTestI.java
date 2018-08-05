package com.task.userRegistration.service;

import com.task.userRegistration.model.User;
import com.task.userRegistration.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTestI {

    private static final String USERNAME = "bartek";
    private static final String PASSWORD = "January1";

    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void shouldDetectThatTheGivenUserHasAlreadyBeenSaved() {

        //Arrange
        User user = new User(USERNAME,PASSWORD);

        // Act
        userService.saveUser(user);

        // Assert
       assertTrue(userService.isUserAlreadyPresent(user));
    }
}