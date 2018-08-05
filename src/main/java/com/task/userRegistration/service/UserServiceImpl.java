package com.task.userRegistration.service;

import com.task.userRegistration.model.User;
import com.task.userRegistration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }


    @Override
    public boolean isUserAlreadyPresent(User user) {

        User retrievedUser = userRepository.findByUsername(user.getUsername());

        return retrievedUser != null;
    }
}
