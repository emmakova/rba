package com.rba.interview_be.mapper;

import com.rba.interview_be.controller.dto.CardStatusDto;
import com.rba.interview_be.entities.UserCardStatusEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CardStatusMapper {

    public static CardStatusDto toDto(UserCardStatusEntity entity) {
        if (entity == null) {
            return null;
        }

        return new CardStatusDto(entity.getId(), entity.getUser().getId(), LocalDateTime.ofInstant(entity.getCreatedAt(), ZoneId.of("UTC")), entity.getStatus());
    }

}
