package com.rodemark.controllers;

import com.rodemark.api.ApiService;
import com.rodemark.api.models.Coordinates;
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
    public String addNewLocation(@RequestParam("cityName") String cityName, @RequestParam("latitude") double latitude,
                                 @RequestParam("longitude") double longitude, HttpServletResponse response,
                                 @CookieValue(value = "session_id", defaultValue = "") String session_id) {

        UserAccount user = sessionService.getUserByUUID(session_id);
        response.addCookie(sessionService.getCleanCookie());

        if (user == null) return "redirect:/login";

        Weather weather = new Weather();
        weather.setNameOfCity(cityName);
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(latitude);
        coordinates.setLongitude(longitude);
        weather.setCoordinates(coordinates);

        locationService.saveLocation(weather, user);

        return "redirect:/home";
    }

    @GetMapping("/locations/show-city")
    public String showFoundLocation(@RequestParam(value = "cityName", required = false) String cityName,
                                    @CookieValue(value = "session_id", defaultValue = "") String session_id, Model model) {
        if (cityName == null || cityName.isEmpty()) {
            return "redirect:/home";
        }

        Weather weather = apiService.getWeatherByName(cityName);

        if (weather == null){
            return "redirect:/search?cityName=" + URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        }

        model.addAttribute("weather", weather);
        model.addAttribute("login", sessionService.getUserByUUID(session_id).getLogin());
        return "found-city";
    }
}