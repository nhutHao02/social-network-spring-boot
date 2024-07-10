package com.nhuthao02.social_network.mapper;

import com.nhuthao02.social_network.dtos.requests.location.LocationRequest;
import com.nhuthao02.social_network.entities.Location;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location locationRequestToLocation(LocationRequest request);

    void updateLocation(@MappingTarget Location location, LocationRequest locationRequest);
}
