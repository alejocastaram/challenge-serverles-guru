package com.alejandrocastaneda.serverles_guru_challenge.infrastructure.adapter.inbound.exceptionhandler;

import com.alejandrocastaneda.serverles_guru_challenge.application.exception.BusinessException;
import com.alejandrocastaneda.serverles_guru_challenge.application.exception.FootballMatchNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FootballMatchNotFoundException.class)
    public ResponseEntity<String> handleMatchNotFound(FootballMatchNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
