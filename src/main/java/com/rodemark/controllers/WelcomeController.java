package com.rodemark.controllers;

import com.rodemark.models.Location;
import com.rodemark.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    private final SessionService sessionService;

    @Autowired
    public WelcomeController(SessionService sessionService){
        this.sessionService = sessionService;
    }
    @GetMapping("/")
    public String welcome(@CookieValue(value = "session_id", defaultValue = "") String sessionUuid, Model model) {
        if (!sessionUuid.isEmpty()){

            // TODO

            return "redirect:/home";
        }
        model.addAttribute("location", new Location());
        return "main-not-auth";
    }
}
