package com.nhuthao02.social_network.services.impl;

import com.nhuthao02.social_network.dtos.requests.user.UserCreationRequest;
import com.nhuthao02.social_network.dtos.requests.user.UserLoginRequest;
import com.nhuthao02.social_network.dtos.requests.user.UserUpdateRequest;
import com.nhuthao02.social_network.dtos.responses.user.UserInfoResponse;
import com.nhuthao02.social_network.entities.Location;
import com.nhuthao02.social_network.entities.User;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.mapper.LocationMapper;
import com.nhuthao02.social_network.mapper.UserMapper;
import com.nhuthao02.social_network.repositories.LocationRepository;
import com.nhuthao02.social_network.repositories.UserRepository;
import com.nhuthao02.social_network.services.IUserService;
import com.nhuthao02.social_network.utils.JwtToken;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService implements IUserService {
    final
    UserRepository userRepository;

    final
    LocationRepository locationRepository;

    final
    UserMapper userMapper;

    final
    LocationMapper locationMapper;

    final
    JwtToken jwtToken;

    public UserService(UserRepository userRepository, LocationRepository locationRepository, UserMapper userMapper, LocationMapper locationMapper, JwtToken jwtToken) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.userMapper = userMapper;
        this.locationMapper = locationMapper;
        this.jwtToken = jwtToken;
    }

    @Override
    public String createUser(UserCreationRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) throw new AppException(ErrorCode.USER_EXIST);

        User user = userMapper.userCreationToUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Location location = Location.builder().build();
        locationRepository.save(location);
        user.setLocation(location);

        userRepository.save(user);
        return user.getId();
    }

    @Override
    public String login(UserLoginRequest request) {

        Optional<User> userOptional = userRepository.findByUserName(request.getUserName());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return jwtToken.generateToken(request.getUserName());
            }
        }
        return null;
    }

    @Override
    public boolean update(String userName, UserUpdateRequest request) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.userResponseToUser(user, request);

        locationMapper.updateLocation(user.getLocation(), request.getLocationRequest());

        userRepository.save(user);
        return true;
    }

    @Override
    public UserInfoResponse getInfoById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.userToUserInfoResponse(user);
    }

    @Override
    public UserInfoResponse getInfo(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.userToUserInfoResponse(user);
    }


}
