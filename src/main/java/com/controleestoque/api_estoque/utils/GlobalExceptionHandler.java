package com.controleestoque.api_estoque.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.controleestoque.api_estoque.utils.exceptions.BadRequestException;
import com.controleestoque.api_estoque.utils.exceptions.OutOfStock;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(BadRequestException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("bad request error", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(OutOfStock.class)
    public ResponseEntity<Map<String, String>> handleOutOfStock(OutOfStock ex) {
        Map<String, String> body = new HashMap<>();
        body.put("unauthorized error", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }
    
}
