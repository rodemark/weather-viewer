package com.rodemark.controllers;

import com.rodemark.DTO.PasswordDTO;
import com.rodemark.models.User;
import com.rodemark.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SettingsController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SettingsController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/settings")
    public String showSettingsPage(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("passwordDTO", new PasswordDTO());
        if (session.getAttribute("alert") != null) {
            model.addAttribute("alert", session.getAttribute("alert"));
            session.removeAttribute("alert");
        }
        return "settings";
    }

    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute("passwordDTO") PasswordDTO passwordDTO, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        model.addAttribute("user", user);
        String errorMessage = "";
        if (!passwordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword())) {
            errorMessage = "Incorrect password!";
            model.addAttribute("error", errorMessage);
            return "settings";
        }

        if (!passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())) {
            errorMessage = "Passwords are not same!";
            model.addAttribute("error", errorMessage);
            return "settings";
        }

        user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
        userService.save(user);
        session.setAttribute("alert", "You successfully changed your password!");

        session.removeAttribute("user");
        session.setAttribute("user", user);

        return "redirect:/settings";
    }
}
