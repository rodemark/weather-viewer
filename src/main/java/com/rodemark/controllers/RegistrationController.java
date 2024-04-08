package com.rodemark.controllers;

import com.rodemark.models.UserAccount;
import com.rodemark.services.UserService;
import com.rodemark.util.BCryptPassword;
import com.rodemark.util.CryptPassword;
import com.rodemark.validators.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final CryptPassword cryptPassword;

    @Autowired
    public RegistrationController(UserService userService, UserValidator userValidator, BCryptPassword cryptPassword){
        this.userService = userService;
        this.userValidator = userValidator;
        this.cryptPassword = cryptPassword;
    }

    @GetMapping("/registration")
    public String registration(@CookieValue(value = "session_id", defaultValue = "") String session_id, Model model){
        if (!session_id.isEmpty()){
            return "redirect:/home";
        }
        model.addAttribute("userAccount", new UserAccount());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute @Valid UserAccount userAccount, @RequestParam("confirm_password") String confirmPassword, BindingResult bindingResult){
        userValidator.validate(userAccount, bindingResult);
        userValidator.validatePassword(userAccount.getPassword(), confirmPassword, bindingResult);

        if (bindingResult.hasErrors()){
            return "registration";
        }

        String encryptPassword = cryptPassword.getEncryptPassword(userAccount.getPassword());
        userAccount.setPassword(encryptPassword);
        userService.save(userAccount);

        return "redirect:/authorization";
    }
}
