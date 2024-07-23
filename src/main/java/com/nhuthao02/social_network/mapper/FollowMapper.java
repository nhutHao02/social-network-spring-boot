package com.nhuthao02.social_network.mapper;

import com.nhuthao02.social_network.dtos.requests.follow.FollowRequest;
import com.nhuthao02.social_network.entities.Follow;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FollowMapper {
    Follow requestToFollow(FollowRequest request);
}
