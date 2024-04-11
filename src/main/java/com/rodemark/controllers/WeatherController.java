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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class WeatherController {

    private final ApiService apiService;
    private final LocationService locationService;
    private final SessionService sessionService;

    @Autowired
    public WeatherController(ApiService apiService, LocationService locationService, SessionService sessionService){
        this.apiService = apiService;
        this.locationService = locationService;
        this.sessionService = sessionService;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping("/home")
    public String home(@ModelAttribute @Valid UserAccount user, Model model) {
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchCityByName(@RequestParam("cityName") String cityName, Model model) {
        if (cityName.isEmpty()){
            return "redirect:/home";
        }
        try {
            Weather weather = apiService.getWeatherByName(cityName);
            model.addAttribute("weather", weather);
        }
        catch (Exception e){
            return "redirect:/home";
        }

        return "redirect:/locations/add?cityName=" + URLEncoder.encode(cityName, StandardCharsets.UTF_8);
    }

    @PostMapping("/locations/add")
    public String addNewLocation(@ModelAttribute("weather") Weather weather,
                                 @CookieValue(value = "session_id", defaultValue = "") String session_id,
                                 BindingResult bindingResult, Model model) {
        System.out.println("Received POST request to add new location with cityName: " + weather.getNameOfCity());
        UserAccount user = sessionService.getUserByUUID(session_id);
        locationService.saveLocation(weather, user);
        return "redirect:/home";
    }

    @GetMapping("/error")
    public String getError(){
        return "error";
    }

    @GetMapping("/locations/add")
    public String addNewLocation(@RequestParam(value = "cityName", required = false) String cityName, Model model) {
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
