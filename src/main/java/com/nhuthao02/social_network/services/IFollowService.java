package com.nhuthao02.social_network.services;

import com.nhuthao02.social_network.dtos.requests.follow.FollowRequest;
import com.nhuthao02.social_network.dtos.responses.follow.FollowResponse;

import java.util.List;

public interface IFollowService {
    boolean add(FollowRequest request);

    boolean delete(FollowRequest request);

    List<FollowResponse> getFollower(String id, Integer page, Integer limit);

    List<FollowResponse> getFollowing(String id, Integer page, Integer limit);

}
