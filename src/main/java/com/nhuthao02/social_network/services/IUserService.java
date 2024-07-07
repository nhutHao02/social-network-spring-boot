package com.nhuthao02.social_network.services;

import com.nhuthao02.social_network.dtos.requests.UserCreationRequest;
import com.nhuthao02.social_network.dtos.requests.UserLoginRequest;

public interface IUserService {
    String createUser(UserCreationRequest request) throws Exception;
    String login(UserLoginRequest request);
}
