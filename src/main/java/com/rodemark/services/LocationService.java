package com.rodemark.services;

import com.rodemark.api.models.Weather;
import com.rodemark.models.Location;
import com.rodemark.models.UserAccount;
import com.rodemark.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository){
        this.locationRepository = locationRepository;
    }

    @Transactional
    public void saveLocation(Weather weather, UserAccount userAccount){
        Location location = new Location();
        location.setName(weather.getNameOfCity());
        location.setLongitude(weather.getCoordinates().getLongitude());
        location.setLatitude(weather.getCoordinates().getLatitude());
        location.setUserAccount(userAccount);

        locationRepository.save(location);
    }

}
