package com.nhuthao02.social_network.mapper;

import com.nhuthao02.social_network.dtos.requests.UserCreationRequest;
import com.nhuthao02.social_network.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "bio", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "urlAvt", ignore = true)
    @Mapping(target = "urlBackground", ignore = true)
    User userCreationToUser(UserCreationRequest request);
}
