package com.task.userRegistration.controller;

import com.task.userRegistration.model.User;
import com.task.userRegistration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    private static final String USERNAME_EXISTS_MESSAGE = "This username already exists.";
    private static final String USERNAME_EXISTS_CODE = "username.exists";

    private UserService userService;

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

        if (userService.isUserAlreadyPresent(user)) {
            errors.rejectValue("username", USERNAME_EXISTS_CODE, USERNAME_EXISTS_MESSAGE);
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
