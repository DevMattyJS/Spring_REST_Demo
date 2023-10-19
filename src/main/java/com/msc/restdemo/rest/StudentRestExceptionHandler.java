package com.msc.restdemo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class StudentRestExceptionHandler {

    // exception handler for StudentNotFoundException
    // <StudentErrorResponse> - type of the response body
    // StudentNotFoundException - exception type to handle / catch
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException (StudentNotFoundException e) {

        // create StudentErrorResponse
        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return new ResponseEntity(errorBody, status)
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // exception handler for generic exceptions (catch all)
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException (Exception e) {

        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }
}
