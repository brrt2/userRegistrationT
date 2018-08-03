package com.task.userRegistration.controller;

import com.task.userRegistration.model.User;
import com.task.userRegistration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;

@Controller
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showMainpage(Model model) {

        User user = new User();
        model.addAttribute("user", user);
        return "createUser";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute @Valid User user, Errors errors, Model model) {

        User retrievedUser = userRepository.findByUsername(user.getUsername());

        if (retrievedUser != null) {
            errors.rejectValue("username","error.username","Username already exists !");
        }

        if (errors.hasErrors()) {
            return "createUser";
        }

        userRepository.save(user);
        return "userCreated";
    }


}
