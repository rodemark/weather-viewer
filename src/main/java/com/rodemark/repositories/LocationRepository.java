package com.rodemark.repositories;

import com.rodemark.models.Location;
import com.rodemark.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{
    List<Location> findByUser(User userAccount);

    void deleteByUserAndLongitudeAndLatitudeAndName(User user, @NotNull double longitude, @NotNull double latitude, @NotBlank String name);
    List<Location> findByUserAndLongitudeAndLatitudeAndName(User user, double longitude, double latitude, String name);
}
