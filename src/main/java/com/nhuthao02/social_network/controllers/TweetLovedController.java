package com.nhuthao02.social_network.controllers;

import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.services.ILoveTweetService;
import com.nhuthao02.social_network.utils.ApiResponse;
import com.nhuthao02.social_network.utils.JwtToken;
import com.nhuthao02.social_network.utils.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(path = "/api/v1/tweet-love")
public class TweetLovedController {
    final
    JwtToken jwtToken;

    final
    ILoveTweetService loveTweetService;

    public TweetLovedController(JwtToken jwtToken, ILoveTweetService loveTweetService) {
        this.jwtToken = jwtToken;
        this.loveTweetService = loveTweetService;
    }

    @PostMapping(value = "/love")
    public ResponseEntity<ApiResponse> love(@RequestParam(name = "userName") String userName, @RequestParam(name = "tweetId") String tweetId, HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        String name = jwtToken.getUsernameFromToken(token);
        if (!name.equals(userName)) throw new AppException(ErrorCode.INVALID_USER);
        boolean rs = loveTweetService.loveTweet(userName, tweetId);
        return rs ?
                ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .message(ResponseCode.SUCCESS.getMessage())
                        .build())
                :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                        .code(ResponseCode.FAILURE.getCode())
                        .message(ResponseCode.FAILURE.getMessage())
                        .build())
                ;
    }

    @DeleteMapping(value = "/delete-tweet-loved")
    public ResponseEntity<ApiResponse> deleteTweetLoved(@RequestParam(name = "userName") String userName, @RequestParam(name = "tweetId") String tweetId, HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        String name = jwtToken.getUsernameFromToken(token);
        if (!name.equals(userName)) throw new AppException(ErrorCode.INVALID_USER);
        boolean rs = loveTweetService.deleteTweetLoved(userName, tweetId);
        return rs ?
                ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .message(ResponseCode.SUCCESS.getMessage())
                        .build())
                :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                        .code(ResponseCode.FAILURE.getCode())
                        .message(ResponseCode.FAILURE.getMessage())
                        .build())
                ;
    }

    @GetMapping(value = "/get-tweet-loved/{id}")
    public ResponseEntity<ApiResponse> getTweetLoved(@PathVariable String id,
                                                     @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(name = "limit", defaultValue = "10") Integer limit,
                                                     HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);

        List<TweetResponse> tweetResponse = loveTweetService.getTweetLoved(id, page, limit);
        return !tweetResponse.isEmpty() ?
                ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .message(ResponseCode.SUCCESS.getMessage())
                        .data(tweetResponse)
                        .build())
                :
                ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .message(ResponseCode.SUCCESS.getMessage())
                        .data(new ArrayList<>())
                        .build())
                ;
    }


}
