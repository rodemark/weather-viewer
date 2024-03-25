package com.rodemark.repositories;
import com.rodemark.models.Location;
import com.rodemark.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{
    List<Location> findByUser(User user);
}
