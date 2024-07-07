package com.nhuthao02.social_network.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ResponseCode {
    SUCCESS("200", "Successfully"),
    FAILURE("500", "Failure"),
    INVALID("400", "Invalid request"),
    UNKNOWN("999", "Unknown error");
    final String code;
    final String message;
}
