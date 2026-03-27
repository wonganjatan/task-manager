package com.wonganjatan.taskmanager.exception;

import com.wonganjatan.taskmanager.exception.auth.LoginException;
import com.wonganjatan.taskmanager.exception.auth.UsernameAlreadyExistException;
import com.wonganjatan.taskmanager.exception.jwt.ExpiredJwtTokenException;
import com.wonganjatan.taskmanager.exception.jwt.InvalidJwtTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDate.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Internal Server Error");
        response.put("message", e.getMessage());
        response.put("path", request.getDescription(false).replace("uri=", ""));

        return new  ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Object> handleLoginException(LoginException e, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDate.now());
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("error", "Unauthorized");
        response.put("message", e.getMessage());
        response.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<Object> handleUsernameAlreadyExistException(UsernameAlreadyExistException e, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDate.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Username already exist");
        response.put("message", e.getMessage());
        response.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDate.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        Map<String, String> fieldErrors = e.getBindingResult().getFieldErrors()
                        .stream()
                                .collect(Collectors.toMap(
                                        FieldError::getField,
                                        FieldError::getDefaultMessage
                                ));
        response.put("message", fieldErrors);
        response.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtTokenException.class)
    public ResponseEntity<Object> handleExpiredJwtTokenException(ExpiredJwtTokenException e, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDate.now());
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("error", "Unauthorized");
        response.put("message", e.getMessage());
        response.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<Object> handleInvalidJwtTokenException(InvalidJwtTokenException e, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDate.now());
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("error", "Unauthorized");
        response.put("message", e.getMessage());
        response.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<Object> handleInvalidDateException(InvalidDateException e, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDate.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", e.getMessage());
        response.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
