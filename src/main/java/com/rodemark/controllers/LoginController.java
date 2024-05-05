package com.rodemark.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController implements AuthenticationFailureHandler {

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(required = false) String error, HttpServletRequest request) {
        if (error != null) {
            request.setAttribute("error", "Incorrect username or password");
        }
        return "authorization";
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        request.setAttribute("error", "Incorrect username or password");
    }
}
