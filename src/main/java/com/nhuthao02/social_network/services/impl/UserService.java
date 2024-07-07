package com.nhuthao02.social_network.services.impl;

import com.nhuthao02.social_network.dtos.requests.UserCreationRequest;
import com.nhuthao02.social_network.dtos.requests.UserLoginRequest;
import com.nhuthao02.social_network.entities.User;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.mapper.UserMapper;
import com.nhuthao02.social_network.repositories.UserRepository;
import com.nhuthao02.social_network.services.IUserService;
import com.nhuthao02.social_network.utils.JwtToken;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtToken jwtToken;

    @Override
    public String createUser(UserCreationRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) throw new AppException(ErrorCode.USER_EXIST);

        User user = userMapper.userCreationToUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return user.getId();
    }

    @Override
    public String login(UserLoginRequest request) {

        Optional<User> userOptional = userRepository.findByUserName(request.getUserName());
         if(userOptional.isPresent())  {
            User user = userOptional.get();
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            if(passwordEncoder.matches(request.getPassword(),user.getPassword())){
                return jwtToken.generateToken(request.getUserName());
            }
         }
        return null;
    }
}
