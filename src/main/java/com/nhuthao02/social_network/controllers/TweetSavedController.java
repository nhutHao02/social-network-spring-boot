package com.nhuthao02.social_network.controllers;

import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.services.ISavedTweetService;
import com.nhuthao02.social_network.utils.ApiResponse;
import com.nhuthao02.social_network.utils.JwtToken;
import com.nhuthao02.social_network.utils.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(path = "/api/v1/tweet-saved")
public class TweetSavedController {
    @Autowired
    ISavedTweetService service;

    @Autowired
    JwtToken jwtToken;

    @PostMapping(value = "save")
    public ResponseEntity<ApiResponse> repost(@RequestParam(name = "userName") String userName, @RequestParam(name = "tweetId") String tweetId, HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        String name = jwtToken.getUsernameFromToken(token);
        if (!name.equals(userName)) throw new AppException(ErrorCode.INVALID_USER);
        boolean rs = service.save(userName, tweetId);
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

    @DeleteMapping(value = "/delete-tweet-save")
    public ResponseEntity<ApiResponse> deleteTweetLoved(@RequestParam(name = "userName") String userName, @RequestParam(name = "tweetId") String tweetId, HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        String name = jwtToken.getUsernameFromToken(token);
        if (!name.equals(userName)) throw new AppException(ErrorCode.INVALID_USER);
        boolean rs = service.deleteTweetSaved(userName, tweetId);
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

    @GetMapping(value = "/get-saved-tweet")
    public ResponseEntity<ApiResponse> getRepostTweets(@RequestParam(name = "userName") String userName,
                                                       @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(name = "limit", defaultValue = "10") Integer limit,
                                                       HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        String name = jwtToken.getUsernameFromToken(token);
        if (!name.equals(userName)) throw new AppException(ErrorCode.INVALID_USER);
        List<TweetResponse> tweetResponse = service.getSavedTweet(userName, page, limit);
        return !tweetResponse.isEmpty() ?
                ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .message(ResponseCode.SUCCESS.getMessage())
                        .data(tweetResponse)
                        .build())
                :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                        .code(ResponseCode.FAILURE.getCode())
                        .message(ResponseCode.FAILURE.getMessage())
                        .build())
                ;
    }
}
