package com.rodemark.controllers;

import com.rodemark.api.ApiService;
import com.rodemark.api.general.Coord;
import com.rodemark.api.others.WeatherCurrent;
import com.rodemark.api.others.WeatherDaily;
import com.rodemark.models.Location;
import com.rodemark.models.User;
import com.rodemark.services.LocationService;
import com.rodemark.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
    public String showFoundCity(@RequestParam("cityName") String cityName, Model model, HttpSession session) {
        WeatherCurrent weatherCurrent = apiService.getCurrentWeatherByName(cityName);

        if (weatherCurrent == null) {
            session.setAttribute("error", "City not found!");
            return "redirect:/home";
        }

        User user = (User) session.getAttribute("user");

        model.addAttribute("weather", weatherCurrent);
        model.addAttribute("user", user);

        Location location = new Location();
        location.setName(weatherCurrent.getNameOfCity());
        location.setLatitude(weatherCurrent.getCoord().getLat());
        location.setLongitude(weatherCurrent.getCoord().getLon());
        location.setUser(user);

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
    public String addLocation(@ModelAttribute Location location, Model model, HttpSession session) {
        locationService.saveLocation(location);
        return "redirect:/home";
    }

    @PostMapping("/deleteLocation")
    public String deleteLocation(@ModelAttribute Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        String name = location.getName();
        User user = location.getUser();

        locationService.deleteByUserAndLongitudeAndLatitudeAndName(user, longitude, latitude, name);
        return "redirect:/home";
    }
    @GetMapping("/forecast")
    public String showWeatherForecast(@RequestParam("lat") Double lat, @RequestParam("lon") Double lon,
                                      Model model, HttpSession session) {
        Coord coord = new Coord();
        coord.setLat(lat);
        coord.setLon(lon);
        List<WeatherDaily> weatherDailyList = apiService.getWeatherForecast(coord);
        User user = (User) session.getAttribute("user");

        Map<LocalDate, List<WeatherDaily>> dailyWeatherMap = weatherDailyList.stream()
                .collect(Collectors.groupingBy(weather -> weather.getDateTime().toLocalDate(), TreeMap::new, Collectors.toList()));

        model.addAttribute("dailyWeatherMap", dailyWeatherMap);
        model.addAttribute("user", user);
        return "weather-forecast";
    }

}