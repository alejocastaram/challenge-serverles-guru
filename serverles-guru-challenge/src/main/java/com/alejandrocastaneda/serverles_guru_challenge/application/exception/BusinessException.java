package com.alejandrocastaneda.serverles_guru_challenge.application.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
