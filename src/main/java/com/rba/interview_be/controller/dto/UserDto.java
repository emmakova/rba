package com.rba.interview_be.controller.dto;

import com.rba.interview_be.enums.CardStatusEnum;

public record UserDto(
        Integer id,
        String firstName,
        String lastName,
        String oib,
        CardStatusEnum cardStatus
) {}
