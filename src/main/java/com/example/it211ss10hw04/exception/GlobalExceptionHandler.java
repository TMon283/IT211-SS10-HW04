package com.example.it211ss10hw04.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(PromotionException.class)
    public ResponseEntity<Map<String, Object>> handleBusiness(PromotionException ex) {
        log.warn("Business error: {}", ex.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put("status", 400);
        body.put("errorCode", "PROMO_INVALID");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleSystem(Exception ex) {
        log.error("System error", ex);
        Map<String, Object> body = new HashMap<>();
        body.put("status", 500);
        body.put("message", "Lỗi máy chủ nội bộ. Vui lòng liên hệ quản trị viên");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}

