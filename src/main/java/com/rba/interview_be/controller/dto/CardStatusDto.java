package com.rba.interview_be.controller.dto;

import com.rba.interview_be.enums.CardStatusEnum;

import java.time.LocalDateTime;

public record CardStatusDto (Integer id, Integer userId, LocalDateTime createdAt, CardStatusEnum cardStatus) {
}
