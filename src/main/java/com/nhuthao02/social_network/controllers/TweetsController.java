package com.nhuthao02.social_network.controllers;

import com.nhuthao02.social_network.dtos.requests.tweet.PostTweetRequest;
import com.nhuthao02.social_network.dtos.requests.tweet.UpdateTweetRequest;
import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.services.ITweetService;
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
@CrossOrigin
@RequestMapping(path = "/api/v1/tweet")
public class TweetsController {

    final
    ITweetService tweetService;

    final
    JwtToken jwtToken;

    public TweetsController(ITweetService tweetService, JwtToken jwtToken) {
        this.tweetService = tweetService;
        this.jwtToken = jwtToken;
    }


    @PostMapping(value = "/post")
    public ResponseEntity<ApiResponse> postTweet(@RequestBody PostTweetRequest postTweetRequest, HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);

        boolean rs = tweetService.addTweet(postTweetRequest);
        if (rs)
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .message(ResponseCode.SUCCESS.getMessage())
                    .build());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                .code(ResponseCode.FAILURE.getCode())
                .message(ResponseCode.FAILURE.getMessage())
                .build());
    }

    @PutMapping(value = "/update-tweet")
    public ResponseEntity<ApiResponse> updateTweet(@RequestBody UpdateTweetRequest updateTweetRequest, HttpServletRequest servletRequest) {

        String token = jwtToken.getBearToken(servletRequest);

        if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);

        boolean rs = tweetService.updateTweet(updateTweetRequest);
        if (rs)
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .message(ResponseCode.SUCCESS.getMessage())
                    .build());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                .code(ResponseCode.FAILURE.getCode())
                .message(ResponseCode.FAILURE.getMessage())
                .build());
    }

    @DeleteMapping(value = "/delete-tweet/{id}")
    public ResponseEntity<ApiResponse> deleteTweet(@PathVariable String id, HttpServletRequest servletRequest) {

        String token = jwtToken.getBearToken(servletRequest);

        if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);

        boolean rs = tweetService.deleteTweet(id);
        if (rs)
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .message(ResponseCode.SUCCESS.getMessage())
                    .build());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                .code(ResponseCode.FAILURE.getCode())
                .message(ResponseCode.FAILURE.getMessage())
                .build());
    }

    @GetMapping(value = "/tweets")
    public ResponseEntity<ApiResponse> getTweets(@RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "limit", defaultValue = "10") Integer limit, HttpServletRequest servletRequest) {

        String token = jwtToken.getBearToken(servletRequest);

        if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);

        List<TweetResponse> responses = tweetService.getTweets(page, limit);
        if (!responses.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .message(ResponseCode.SUCCESS.getMessage())
                    .data(responses)
                    .build());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.FAILURE.getMessage())
                .data(new ArrayList<>())
                .build());
    }

    @GetMapping(value = "/tweets/{userID}")
    public ResponseEntity<ApiResponse> getTweetsByUserID(@PathVariable String userID, @RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "limit", defaultValue = "10") Integer limit, HttpServletRequest servletRequest) {

        String token = jwtToken.getBearToken(servletRequest);

        if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);

        List<TweetResponse> responses = tweetService.getTweetsByUserID(userID, page, limit);
        if (!responses.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .message(ResponseCode.SUCCESS.getMessage())
                    .data(responses)
                    .build());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.FAILURE.getMessage())
                .data(new ArrayList<>())
                .build());
    }
}
