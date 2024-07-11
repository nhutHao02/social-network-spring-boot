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
//    USER_NOT_FOUND("003", "User not found"),
    ;
    final String code;
    final String message;
}
