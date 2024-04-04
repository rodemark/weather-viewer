package com.rodemark.controllers;

import com.rodemark.models.UserAccount;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WeatherController {

    @GetMapping("/home")
    public String home(){

        return "/home";
    }

    @PostMapping("/home")
    public String home(@ModelAttribute @Valid UserAccount user){

        return "redirect:/";
    }
}
