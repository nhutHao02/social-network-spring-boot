package com.nhuthao02.social_network.controllers;

import com.nhuthao02.social_network.dtos.requests.UserCreationRequest;
import com.nhuthao02.social_network.dtos.requests.UserLoginRequest;
import com.nhuthao02.social_network.dtos.responses.UserLoginResponse;
import com.nhuthao02.social_network.services.IUserService;
import com.nhuthao02.social_network.utils.ApiResponse;
import com.nhuthao02.social_network.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthencationController {
    @Autowired
    IUserService userService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<ApiResponse> create(@RequestBody UserCreationRequest request) throws Exception {
        String result = userService.createUser(request);
        if (!result.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .message(ResponseCode.SUCCESS.getMessage())
                    .build());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                .code(ResponseCode.FAILURE.getCode())
                .message(ResponseCode.FAILURE.getMessage())
                .build());
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<ApiResponse> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                .data("Get all success.")
                .build());
    }

    @PostMapping(value = "/log-in")
    public ResponseEntity<ApiResponse> create(@RequestBody UserLoginRequest request) {
        String token = userService.login(request);
        if (!token.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .message(ResponseCode.SUCCESS.getMessage())
                    .data(UserLoginResponse.builder().token(token).build())
                    .build());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                .code(ResponseCode.FAILURE.getCode())
                .message(ResponseCode.FAILURE.getMessage())
                .build());
    }

}
