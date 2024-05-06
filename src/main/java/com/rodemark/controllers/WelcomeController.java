package com.rodemark.controllers;

import com.rodemark.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    private final UserValidator userValidator;

    @Autowired
    public WelcomeController(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    @GetMapping("/")
    public String welcomePage() {
        if (userValidator.isAuthenticated()) return "redirect:/home";
        return "welcomePage";
    }

}
