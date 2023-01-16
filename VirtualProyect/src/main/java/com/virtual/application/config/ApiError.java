package com.virtual.application.config;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiError extends RuntimeException {

    private HttpStatus status;
    private String message;

    public ApiError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

}