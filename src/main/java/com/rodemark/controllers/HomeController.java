package com.rodemark.controllers;

import com.rodemark.api.ApiService;
import com.rodemark.api.others.WeatherCurrent;
import com.rodemark.models.Location;
import com.rodemark.models.User;
import com.rodemark.services.LocationService;
import com.rodemark.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final UserService userService;
    private final LocationService locationService;
    private final ApiService apiService;

    @Autowired
    public HomeController(UserService userService, LocationService locationService, ApiService apiService) {
        this.userService = userService;
        this.locationService = locationService;
        this.apiService = apiService;
    }

    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session) {
        User user = userService.getCurrentUser();
        session.setAttribute("user", user);

        List<Location> locations = locationService.findAll(user);
        List<WeatherCurrent> weatherCurrentList = apiService.getCurrentWeathersByLocations(locations);

        model.addAttribute("weathers", weatherCurrentList);
        model.addAttribute("user", user);
        model.addAttribute("location", new Location());
        if (session.getAttribute("error") != null) {
            model.addAttribute("error", session.getAttribute("error"));
            session.removeAttribute("error");
        }

        return "home";
    }
}
