package com.nhuthao02.social_network.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    USER_EXIST("001", "User already exists"),
    INVALID_PASSWORD("002", "Invalid password"),
    USER_NOT_FOUND("003", "User not found"),
    INVALID_TOKEN("004", "Invalid token"),
    INVALID_USER("005", "Invalid user"),
    GENERATE_TOKEN_FAILURE("006", "Generate token failure"),
    TWEET_NOT_FOUND("007", "Tweet not found"),
    TWEET_SAVED_NOT_FOUND("008", "TweetSaved not found"),
    TWEET_LOVED_NOT_FOUND("009", "TweetLoved not found"),
    TWEET_REPOSTED_NOT_FOUND("010", "TweetReposted not found"),
    COMMENT_NOT_FOUND("011", "Comment not found"),
    ;
    final String code;
    final String message;
}
