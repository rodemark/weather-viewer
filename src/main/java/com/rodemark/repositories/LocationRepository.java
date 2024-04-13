package com.rodemark.repositories;

import com.rodemark.models.Location;
import com.rodemark.models.UserAccount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{
    List<Location> findByUserAccount(UserAccount userAccount);

    void deleteByUserAccountAndLongitudeAndLatitudeAndName(UserAccount userAccount, @NotNull double longitude, @NotNull double latitude, @NotBlank String name);
    List<Location> findByUserAccountAndLongitudeAndLatitudeAndName(UserAccount userAccount, double longitude, double latitude, String name);
}
