package com.nhuthao02.social_network.exception;

import com.nhuthao02.social_network.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(
                        HttpStatus.BAD_REQUEST).body(ApiResponse
                        .builder()
                        .code("400")
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppxception(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body(ApiResponse
                .builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
    }
}
