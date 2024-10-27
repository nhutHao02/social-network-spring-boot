package com.nhuthao02.social_network.controllers;

import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.services.IRepostTweetService;
import com.nhuthao02.social_network.utils.ApiResponse;
import com.nhuthao02.social_network.utils.JwtToken;
import com.nhuthao02.social_network.utils.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(path = "/api/v1/tweet-repost")
public class TweetRepostedController {
    final
    IRepostTweetService service;

    final
    JwtToken jwtToken;

    public TweetRepostedController(IRepostTweetService service, JwtToken jwtToken) {
        this.service = service;
        this.jwtToken = jwtToken;
    }

    @PostMapping(value = "repost")
    public ResponseEntity<ApiResponse> repost(@RequestParam(name = "userName") String userName, @RequestParam(name = "tweetId") String tweetId, HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        String name = jwtToken.getUsernameFromToken(token);
        if (!name.equals(userName)) throw new AppException(ErrorCode.INVALID_USER);
        boolean rs = service.repost(userName, tweetId);
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

    @DeleteMapping(value = "/delete-tweet-repost")
    public ResponseEntity<ApiResponse> deleteTweetLoved(@RequestParam(name = "userName") String userName, @RequestParam(name = "tweetId") String tweetId, HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        String name = jwtToken.getUsernameFromToken(token);
        if (!name.equals(userName)) throw new AppException(ErrorCode.INVALID_USER);
        boolean rs = service.deleteTweetReposted(userName, tweetId);
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

    @GetMapping(value = "/get-repost-tweet/{id}")
    public ResponseEntity<ApiResponse> getRepostTweets(@PathVariable String id,
                                                       @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(name = "limit", defaultValue = "10") Integer limit,
                                                       HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);

        List<TweetResponse> tweetResponse = service.getRepostTweet(id, page, limit);
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
