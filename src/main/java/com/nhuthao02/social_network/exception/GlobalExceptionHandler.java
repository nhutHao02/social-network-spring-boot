package com.nhuthao02.social_network.exception;

import com.nhuthao02.social_network.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

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
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidationException(MethodArgumentNotValidException ex) {
        HashMap<String, String> map = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            map.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body(ApiResponse
                .builder()
                .code("400")
                .message("Invalid request")
                .data(map)
                .build());
    }
}
