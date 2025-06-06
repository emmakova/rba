package com.rba.interview_be.controller.error;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorDto (HttpStatus httpStatus, String errorMessage, LocalDateTime timestamp){
}
