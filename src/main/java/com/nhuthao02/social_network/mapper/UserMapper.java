package com.nhuthao02.social_network.mapper;

import com.nhuthao02.social_network.dtos.requests.user.UserCreationRequest;
import com.nhuthao02.social_network.dtos.requests.user.UserUpdateRequest;
import com.nhuthao02.social_network.dtos.responses.follow.FollowResponse;
import com.nhuthao02.social_network.dtos.responses.user.UserInfoResponse;
import com.nhuthao02.social_network.dtos.responses.user.UserTweetResponse;
import com.nhuthao02.social_network.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "bio", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "urlAvt", ignore = true)
    @Mapping(target = "urlBackground", ignore = true)
    User userCreationToUser(UserCreationRequest request);

    void userResponseToUser(@MappingTarget User user, UserUpdateRequest request);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "createAt", target = "createAt")
    UserInfoResponse userToUserInfoResponse(User user);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "urlAvt", target = "urlAvt")
    FollowResponse userToFollowResponse(User user);

    UserTweetResponse userToUserTweetResponse(User user);
}
