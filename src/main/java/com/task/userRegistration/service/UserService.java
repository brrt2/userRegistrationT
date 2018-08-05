package com.task.userRegistration.service;

import com.task.userRegistration.model.User;

public interface UserService {

    void saveUser(User user);

    boolean isUserAlreadyPresent(User user);

}
