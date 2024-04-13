package com.rodemark.controllers;

import com.rodemark.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    private final SessionService sessionService;

    @Autowired
    public WelcomeController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public String welcome(@CookieValue(value = "session_id", defaultValue = "") String sessionUUID) {
        if (sessionUUID.isEmpty() || sessionService.getSessionByUUID(sessionUUID) == null){
            return "main-not-auth";
        }

        if (!sessionService.isOver(sessionUUID)){
            return "redirect:/home";
        }

        return "main-not-auth";
    }
}
