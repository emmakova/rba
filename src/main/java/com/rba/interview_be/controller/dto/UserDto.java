package com.rba.interview_be.controller.dto;

public record UserDto(
        Integer id,
        String firstName,
        String lastName,
        String oib,
        String cardStatus
) {}
