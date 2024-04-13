package com.rodemark.controllers;

import com.rodemark.api.ApiService;
import com.rodemark.api.models.Weather;
import com.rodemark.models.Location;
import com.rodemark.models.UserAccount;
import com.rodemark.services.LocationService;
import com.rodemark.services.SessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class WeatherController {

    private final ApiService apiService;
    private final SessionService sessionService;
    private final LocationService locationService;

    @Autowired
    public WeatherController(ApiService apiService, LocationService locationService, SessionService sessionService, SessionService sessionService1, LocationService locationService1){
        this.apiService = apiService;
        this.sessionService = sessionService1;
        this.locationService = locationService1;
    }

    @GetMapping("/home")
    public String home(@CookieValue(value = "session_id", defaultValue = "") String sessionUUID, Model model) {
        if (sessionUUID.isEmpty()){
            return "redirect:/";
        }
        UserAccount userAccount = sessionService.getUserByUUID(sessionUUID);
        if (userAccount == null) {
            return "redirect:/login";
        }
        List<Location> locationsList = locationService.getLocations(userAccount);
        List<Weather> weathersList = apiService.getWeathersByLocations(locationsList);

        model.addAttribute("weathersList", weathersList);
        model.addAttribute("login", userAccount.getLogin());
        return "home";
    }

    @PostMapping("/home")
    public String home(@ModelAttribute @Valid UserAccount user, Model model) {
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchCityByName(@RequestParam("cityName") String cityName, Model model) {
        if (cityName.isEmpty()){
            return "/home";
        }

        Weather weather = apiService.getWeatherByName(cityName);

        if (weather == null) {
            return "/home";
        }

        model.addAttribute("weather", weather);

        return "redirect:/locations/show-city?cityName=" + URLEncoder.encode(cityName, StandardCharsets.UTF_8);
    }
}
