package com.virtual.application.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.virtual.application.model.ErrorResponseDto;

	@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ApiError.class)
    public ResponseEntity<ErrorResponseDto> restApiExceptionHandler(ApiError e, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponseDto(e.getMessage()), e.getStatus());
    }

}
