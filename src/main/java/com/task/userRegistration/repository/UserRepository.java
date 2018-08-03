package com.task.userRegistration.repository;

import com.task.userRegistration.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByUsername(String username);

}
