package com.nhuthao02.social_network.repositories;

import com.nhuthao02.social_network.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {
}
