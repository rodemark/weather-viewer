package com.rodemark.controllers;

import com.rodemark.api.ApiService;
import com.rodemark.api.models.Weather;
import com.rodemark.models.Location;
import com.rodemark.models.User;
import com.rodemark.services.LocationService;
import com.rodemark.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {
    private final UserService userService;
    private final LocationService locationService;
    private final ApiService apiService;

    @Autowired
    public WeatherController(UserService userService, LocationService locationService, ApiService apiService) {
        this.userService = userService;
        this.locationService = locationService;
        this.apiService = apiService;
    }

    @GetMapping("/search")
    public String showFoundCity(@RequestParam("cityName") String cityName, Model model) {
        Weather weather = apiService.getWeatherByName(cityName);
        model.addAttribute("weather", weather);
        model.addAttribute("user", userService.getCurrentUser());

        Location location = new Location();
        location.setName(weather.getNameOfCity());
        location.setLatitude(weather.getCoordinates().getLatitude());
        location.setLongitude(weather.getCoordinates().getLongitude());
        location.setUser(userService.getCurrentUser());

        if(locationService.findByUserAndLongitudeAndLatitudeAndName(location.getUser(), location.getLongitude(),
                location.getLatitude(), location.getName()) != null) {

            model.addAttribute("locationAlreadyAdded", true);
        } else {
            model.addAttribute("locationAlreadyAdded", false);
        }
        model.addAttribute("location", location);

        return "found-city";
    }

    @PostMapping("/addLocation")
    public String addLocation(@ModelAttribute @Valid Location location, Model model, HttpSession session) {
        locationService.saveLocation(location);
        return "redirect:/home";
    }

    @PostMapping("/deleteLocation")
    public String deleteLocation(@ModelAttribute @Valid Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        String name = location.getName();
        User user = location.getUser();

        locationService.deleteByUserAndLongitudeAndLatitudeAndName(user, longitude, latitude, name);
        return "redirect:/home";
    }
}
