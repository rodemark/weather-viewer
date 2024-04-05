package com.rodemark.controllers;

import com.rodemark.models.UserAccount;
import com.rodemark.services.SessionService;
import com.rodemark.services.UserService;
import com.rodemark.validators.ExistingAccountValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class AuthorizationController {

    private final UserService userService;
    private final ExistingAccountValidator existingAccountValidator;
    private final SessionService sessionService;

    @Autowired
    public AuthorizationController(UserService userService, ExistingAccountValidator existingAccountValidator, SessionService sessionService){
        this.userService = userService;
        this.existingAccountValidator = existingAccountValidator;
        this.sessionService = sessionService;
    }

    @GetMapping("/authorization")
    public String authorization(@CookieValue(value = "session_id", defaultValue = "") String session_id, Model model){
        if (!session_id.isEmpty()){
            return "redirect:/";
        }
        model.addAttribute("userAccount", new UserAccount());
        return "authorization";
    }

    @PostMapping("/authorization")
    public String authorization(@ModelAttribute("userAccount") @Valid UserAccount userAccount, BindingResult bindingResult, HttpServletResponse response){

        if (bindingResult.hasErrors()){
            return "authorization";
        }
        existingAccountValidator.validate(userAccount, bindingResult);
        if (bindingResult.hasErrors()){
            return "authorization";
        }

        UserAccount foundedUser = userService.findByLoginAndPassword(userAccount);
        UUID uuid = sessionService.saveSessionAndGetUUID(foundedUser);

        Cookie cookie = new Cookie("session_id", uuid.toString());
        cookie.setMaxAge((int) sessionService.getSESSION_TIME());
        response.addCookie(cookie);

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie cookie = new Cookie("session_id", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }
}
