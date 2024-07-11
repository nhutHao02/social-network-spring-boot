package com.nhuthao02.social_network.services;

import com.nhuthao02.social_network.dtos.requests.user.UserCreationRequest;
import com.nhuthao02.social_network.dtos.requests.user.UserLoginRequest;
import com.nhuthao02.social_network.dtos.requests.user.UserUpdateRequest;
import com.nhuthao02.social_network.dtos.responses.user.UserInfoResponse;

public interface IUserService {
    String createUser(UserCreationRequest request) throws Exception;

    String login(UserLoginRequest request);

    boolean update(String userName, UserUpdateRequest request);

    UserInfoResponse getInfoById(String id);

    UserInfoResponse getInfo(String userName);
}
