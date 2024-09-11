package com.example.crud.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class AppControllerAdvice {
    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public ResponseEntity<?> systemErrorException(Exception exception, HttpServletResponse res, HttpServletRequest req) {
        writeErrorStackTrace(exception, req.getMethod() + req.getServletPath());
        return ResponseEntity.status(500)
                .body(Map.of("error", exception.getMessage()));
    }

    private void writeErrorStackTrace(Exception exception, String apiInfo) {
        String stackTrace = "Error Exception: " + exception.getClass().getName() + ExceptionUtils.getStackTrace(exception);
        log.error(stackTrace);
    }
}
