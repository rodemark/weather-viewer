package com.rodemark.controllers;

import com.rodemark.api.ApiService;
import com.rodemark.api.models.Weather;
import com.rodemark.models.UserAccount;
import com.rodemark.services.LocationService;
import com.rodemark.services.SessionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class LocationController {
    private final ApiService apiService;
    private final LocationService locationService;
    private final SessionService sessionService;

    @Autowired
    public LocationController(ApiService apiService, LocationService locationService, SessionService sessionService){
        this.apiService = apiService;
        this.locationService = locationService;
        this.sessionService = sessionService;
    }

    @PostMapping("/locations/add")
    public String addNewLocation(@CookieValue(value = "session_id", defaultValue = "") String session_id,
                                 HttpServletResponse response) {

        UserAccount user = sessionService.getUserByUUID(session_id);
        response.addCookie(sessionService.getCleanCookie());

        if (user == null) return "redirect:/login";

        // TODO
        Weather weather = new Weather();
        locationService.saveLocation(weather, user);

        return "redirect:/home";
    }

    @GetMapping("/locations/show-city")
    public String showFoundLocation(@RequestParam(value = "cityName", required = false) String cityName, Model model) {
        if (cityName == null || cityName.isEmpty()) {
            return "redirect:/home";
        }
        try {
            Weather weather = apiService.getWeatherByName(cityName);
            model.addAttribute("weather", weather);
            model.addAttribute("cityName", cityName);
            return "found-city";
        } catch (Exception e) {
            return "redirect:/search?cityName=" + URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        }
    }
}