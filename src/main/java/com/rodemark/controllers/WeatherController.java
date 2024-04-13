package com.rodemark.controllers;

import com.rodemark.api.ApiService;
import com.rodemark.api.models.Weather;
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

@Controller
public class WeatherController {

    private final ApiService apiService;

    @Autowired
    public WeatherController(ApiService apiService, LocationService locationService, SessionService sessionService){
        this.apiService = apiService;
    }

    @GetMapping("/home")
    public String home(@CookieValue(value = "session_id", defaultValue = "") String session_id) {
        if (session_id.isEmpty()){
            return "redirect:/";
        }
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
        try {
            Weather weather = apiService.getWeatherByName(cityName);
            model.addAttribute("weather", weather);
        }
        catch (Exception e){
            return "/home";
        }

        return "redirect:/locations/show-city?cityName=" + URLEncoder.encode(cityName, StandardCharsets.UTF_8);
    }
}
