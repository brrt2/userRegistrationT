package com.task.userRegistration.controller;

import com.task.userRegistration.model.User;
import com.task.userRegistration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;
    private static final String USERNAME_EXISTS_MESSAGE = "This username already exists !";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String showMainPage(Model model) {

        User user = new User();
        model.addAttribute("user", user);
        return "createUser";
    }

    @PostMapping(value = "/users")
    public String saveUser(@ModelAttribute @Valid User user, Errors errors) {

        User retrievedUser = userService.findByUsername(user.getUsername());

        if (retrievedUser != null) {
            errors.rejectValue("username", USERNAME_EXISTS_MESSAGE);
        }

        if (errors.hasErrors()) {
            return "createUser";
        }

        userService.saveUser(user);

        return "redirect:userCreated";
    }

    @GetMapping(value = "/userCreated")
    public String showUserCreatedPage() {

        return "userCreated";
    }
}
