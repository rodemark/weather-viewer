package com.rodemark.controllers;

import com.rodemark.DTO.UserDTO;
import com.rodemark.models.User;
import com.rodemark.services.UserService;
import com.rodemark.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userService, UserValidator userValidator, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String showAuthorization(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistration(@ModelAttribute("userDTO") UserDTO userDTO,
                                      BindingResult bindingResult, Model model) {

        userValidator.validatePassword(userDTO.getPassword(), userDTO.getConfirmPassword(), bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Passwords are not same!");
            return "registration";
        }

        userValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Such a user already exists!");
            return "registration";
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userService.save(user);
        return "redirect:/login";
    }
}
