package com.task.userRegistration.repository;

import com.task.userRegistration.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTestI {

    private static final String USERNAME = "bartek";
    private static final String PASSWORD = "January1";
    private static final int EXPECTED_USER_LIST_SIZE = 1;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void shouldFindUserByUsernameWhenItHasBeenPersisted() {

        // arrange
        final User user = new User(USERNAME,PASSWORD);
        entityManager.persist(user);

        // act
        final User found = userRepository.findByUsername(USERNAME);
        final List<User> usersPresentInDatabase = (List<User>) userRepository.findAll();

        // assert
        assertNotNull(found);
        assertEquals(user,found);
        assertEquals(EXPECTED_USER_LIST_SIZE,usersPresentInDatabase.size());

    }
}