package com.nhuthao02.social_network.controllers;

import com.nhuthao02.social_network.dtos.requests.user.UserCreationRequest;
import com.nhuthao02.social_network.dtos.requests.user.UserLoginRequest;
import com.nhuthao02.social_network.dtos.requests.user.UserUpdateRequest;
import com.nhuthao02.social_network.dtos.responses.user.UserInfoResponse;
import com.nhuthao02.social_network.dtos.responses.user.UserLoginResponse;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.services.IUserService;
import com.nhuthao02.social_network.utils.ApiResponse;
import com.nhuthao02.social_network.utils.JwtToken;
import com.nhuthao02.social_network.utils.ResponseCode;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthencationController {
    @Autowired
    IUserService userService;

    @Autowired
    JwtToken jwtToken;

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

    @PutMapping(value = "/update/{userName}")
    public ResponseEntity<ApiResponse> create(@PathVariable String userName, @RequestBody UserUpdateRequest request, HttpServletRequest servletRequest) {

        String token = getBearToken(servletRequest);
        String name = null;
        try {
            name = jwtToken.getUsernameFromToken(token);
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        if (!name.equals(userName)) {
            throw new AppException(ErrorCode.INVALID_USER);
        }
        boolean rs = userService.update(userName, request);

        return rs ?
                ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .message(ResponseCode.SUCCESS.getMessage())
                        .build())
                :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                        .code(ResponseCode.FAILURE.getCode())
                        .message(ResponseCode.FAILURE.getMessage())
                        .build());
    }

    @GetMapping(value = "info/{id}")
    public ResponseEntity<ApiResponse> getInfoById(@PathVariable String id, HttpServletRequest servletRequest) throws RuntimeException {
        String token = getBearToken(servletRequest);
        try {
            if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }
        UserInfoResponse userInfoResponse = userService.getInfoById(id);

        return (userInfoResponse != null) ?
                ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .message(ResponseCode.SUCCESS.getMessage())
                        .data(userInfoResponse)
                        .build())
                :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                        .code(ResponseCode.FAILURE.getCode())
                        .message(ResponseCode.FAILURE.getMessage())
                        .build());

    }

    @GetMapping(value = "info")
    public ResponseEntity<ApiResponse> getInfoById(HttpServletRequest servletRequest) throws RuntimeException {
        String token = getBearToken(servletRequest);
        try {
            if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }
        String userName = null;
        try {
            userName = jwtToken.getUsernameFromToken(token);
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
        UserInfoResponse userInfoResponse = userService.getInfo(userName);

        return (userInfoResponse != null) ?
                ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .message(ResponseCode.SUCCESS.getMessage())
                        .data(userInfoResponse)
                        .build())
                :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                        .code(ResponseCode.FAILURE.getCode())
                        .message(ResponseCode.FAILURE.getMessage())
                        .build());

    }

    private String getBearToken(HttpServletRequest servletRequest) {
        String authorizationHeader = servletRequest.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        return authorizationHeader.substring(7);
    }

}
