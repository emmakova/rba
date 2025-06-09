package com.rba.interview_be.controller.error;

import com.rba.interview_be.exceptions.NotFoundException;
import com.rba.interview_be.exceptions.StatusUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFound(NotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(StatusUpdateException.class)
    public ResponseEntity<ErrorDto> handleStatusUpdateException(StatusUpdateException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneric(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred");
    }

    private ResponseEntity<ErrorDto> buildErrorResponse(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus).body(new ErrorDto(httpStatus, message, LocalDateTime.now()));
    }

}
