package com.web.blog.controller.exception;

import java.util.Date;

import com.web.blog.dto.ApiErrorDetail;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiErrorDetail> handlerUserAlreadyExistException(UserAlreadyExistException info){
        ApiErrorDetail errorDetail = new ApiErrorDetail();
        errorDetail.setTimeStamp(new Date());
        errorDetail.setMsg(info.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

   
}