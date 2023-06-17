package com.example.blogApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> responseNotFoundExceptionHandler(ResourceNotFoundException re) {
        return new ResponseEntity<>("User Doesn't Exists ", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgNotValidException(MethodArgumentNotValidException mv) {
        Map<String, String> resp = new HashMap<>();
        mv.getBindingResult().getAllErrors().forEach((objectError -> {
            String fieldName=((FieldError)objectError).getField();
            String message=objectError.getDefaultMessage();
            resp.put(fieldName,message);
        }));
        return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
    }
}
