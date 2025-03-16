package com.sankalp.api_gateway.config;




import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleInternalServerError(RuntimeException exception) {
        ApiError apiError = ApiError.builder()
                                    .status(HttpStatus.UNAUTHORIZED)
                                    .message(exception.getMessage())// Recommended for better tracing
                                    .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

//    @ExceptionHandler(RuntimeConflictException.class)
//    public ResponseEntity<ApiResponse<?>> runtimeConflictException(RuntimeConflictException exception) {
//        ApiError apiError = ApiError.builder()
//                .status(HttpStatus.CONFLICT)
//                .message(exception.getMessage())
//                .build();
//        return buildErrorResponseEntity(apiError);
//    }




}














