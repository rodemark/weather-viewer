package com.rodemark.controllers;

import com.rodemark.models.UserAccount;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthorizationController {

    @GetMapping("/authorization")
    public String authorization(@CookieValue(value = "session_id", defaultValue = "") String session_id, Model model){
        if (!session_id.isEmpty()){
            return "redirect:/";
        }
        model.addAttribute("userAccount", new UserAccount());
        return "authorization";
    }

    @PostMapping("/authorization")
    public String authorization(@ModelAttribute @Valid UserAccount userAccount, BindingResult bindingResult){


        return "redirect:/home";
    }

}
