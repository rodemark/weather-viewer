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

    @GetMapping("/login")
    public String authorization(@CookieValue(value = "session_id", defaultValue = "") String sessionUUID,
                                Model model, HttpServletResponse response){
        if (sessionUUID.isEmpty() || sessionService.getSessionByUUID(sessionUUID) == null){
            model.addAttribute("userAccount", new UserAccount());
            return "authorization";
        }

        if (!sessionService.isOver(sessionUUID)){
            return "redirect:/home";
        } else {
            sessionService.deleteSession(sessionUUID);
            model.addAttribute("userAccount", new UserAccount());
            return "authorization";
        }
    }

    @PostMapping("/login")
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
        response.addCookie(sessionService.getCleanCookie());

        Cookie cookie = new Cookie("session_id", uuid.toString());
        cookie.setMaxAge((int) sessionService.getSESSION_TIME());
        response.addCookie(cookie);

        return "redirect:/home";
    }

    @GetMapping("/logoff")
    public String logout(@CookieValue(name = "session_id", defaultValue = "") String sessionID, HttpServletResponse response){
        System.out.println("Logging out, session ID: " + sessionID);
        sessionService.deleteSession(sessionID);
        response.addCookie(sessionService.getCleanCookie());
        return "redirect:/";
    }
}
