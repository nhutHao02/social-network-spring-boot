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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(path = "/api/v1/auth")
@Tag (name = "Authencation", description = "APIs related to authencation")
public class AuthencationController {
    final
    IUserService userService;

    final
    JwtToken jwtToken;

    public AuthencationController(IUserService userService, JwtToken jwtToken) {
        this.userService = userService;
        this.jwtToken = jwtToken;
    }

    @Operation(
            summary = "Sign up a new user",
            description = "Sign up a new user"
    )
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    )
            }
    )
    @PostMapping(value = "/sign-up")
    public ResponseEntity<ApiResponse> create(@RequestBody UserCreationRequest request) {
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

    @Operation(
            summary = "Log in",
            description = "Log in to get token"
    )
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    )
            }
    )
    @PostMapping(value = "/log-in")
    public ResponseEntity<ApiResponse> login(@RequestBody UserLoginRequest request) {
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

    @Operation(
            summary = "Update user information",
            description = "Update user information"
    )
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    )
            }
    )

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = "/update/{userName}")
    public ResponseEntity<ApiResponse> update(@Parameter(description = "Update user information by user name") @PathVariable String userName, @RequestBody UserUpdateRequest request, HttpServletRequest servletRequest) {

        String token = jwtToken.getBearToken(servletRequest);

        String name = jwtToken.getUsernameFromToken(token);


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

    @Operation(
            summary = "Get user information",
            description = "Get user information"
    )
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    )
            }
    )
    @GetMapping(value = "info/{id}")
    public ResponseEntity<ApiResponse> getInfoById(@PathVariable String id, HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);
        if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);
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
    public ResponseEntity<ApiResponse> getInfoById(HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);

        String userName = jwtToken.getUsernameFromToken(token);
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
}
