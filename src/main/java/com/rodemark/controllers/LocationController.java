package com.rodemark.controllers;

import com.rodemark.api.ApiService;
import com.rodemark.api.WeatherService;
import com.rodemark.api.models.Coordinates;
import com.rodemark.api.models.Weather;
import com.rodemark.api.models.WeatherFromApi;
import com.rodemark.models.UserAccount;
import com.rodemark.services.LocationService;
import com.rodemark.services.SessionService;
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
                                 @RequestParam("longitude") double longitude,
                                 @CookieValue(value = "session_id", defaultValue = "") String sessionUUID) {

        UserAccount userAccount = sessionService.getUserByUUID(sessionUUID);

        if (userAccount == null) return "redirect:/login";

        WeatherFromApi weatherFromApi = new WeatherFromApi();
        weatherFromApi.setNameOfCity(cityName);
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(latitude);
        coordinates.setLongitude(longitude);
        weatherFromApi.setCoordinates(coordinates);

        locationService.saveLocation(weatherFromApi, userAccount);

        return "redirect:/home";
    }

    @GetMapping("/locations/show-city")
    public String showFoundLocation(@RequestParam("cityName") String cityName, @RequestParam("latitude") double latitude,
                                    @RequestParam("longitude") double longitude,
                                    @CookieValue(value = "session_id", defaultValue = "") String session_id, Model model) {
        if (cityName == null || cityName.isEmpty()) {
            return "redirect:/home";
        }

        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(latitude);
        coordinates.setLongitude(longitude);

        WeatherFromApi weatherFromApi = apiService.getWeatherByCoordinates(coordinates);


        if (weatherFromApi == null){
            return "redirect:/search?cityName=" + URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        }

        weatherFromApi.setCoordinates(coordinates);
        Weather weather = WeatherService.weatherTransfer(weatherFromApi);

        model.addAttribute("weather", weather);
        model.addAttribute("login", sessionService.getUserByUUID(session_id).getLogin());
        return "found-city";
    }

    @PostMapping("/locations/delete")
    public String deleteLocation(@CookieValue(value = "session_id", defaultValue = "") String sessionUUID,
                                 @RequestParam("cityName") String cityName, @RequestParam("latitude") double latitude,
                                 @RequestParam("longitude") double longitude){

        UserAccount userAccount = sessionService.getUserByUUID(sessionUUID);
        if (userAccount == null) return "redirect:/login";
        WeatherFromApi weatherFromApi = new WeatherFromApi();

        Coordinates coordinates = new Coordinates();
        coordinates.setLongitude(longitude);
        coordinates.setLatitude(latitude);

        weatherFromApi.setCoordinates(coordinates);
        weatherFromApi.setNameOfCity(cityName);

        System.out.println("Deleting location: " + cityName + ", Latitude: " + latitude + ", Longitude: " + longitude);

        locationService.deleteLocation(weatherFromApi, userAccount);

        return "redirect:/home";
    }
}