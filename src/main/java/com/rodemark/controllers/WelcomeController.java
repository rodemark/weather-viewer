package com.rodemark.controllers;

import com.rodemark.models.Location;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/")
    public String welcome(@CookieValue(value = "session_id", defaultValue = "") String sessionUuid, Model model) {
        if (!sessionUuid.isEmpty()){

            return "redirect:/main";
        }
        model.addAttribute("location", new Location());
        return "welcome";
    }
}
