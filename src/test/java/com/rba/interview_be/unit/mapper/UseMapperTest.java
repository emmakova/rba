package com.rba.interview_be.unit.mapper;

import com.rba.interview_be.controller.dto.CardStatusDto;
import com.rba.interview_be.controller.dto.UserDto;
import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.enums.CardStatusEnum;
import com.rba.interview_be.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UseMapperTest {


    @Test
    void toDto_fromUserEntity_shouldMapFieldsCorrectly() {
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOib("12345678901");

        UserCardStatusEntity status = new UserCardStatusEntity();
        status.setUser(user);
        status.setStatus(CardStatusEnum.APPROVED);
        status.setCreatedAt(Instant.now());

        user.setCardStatuses(List.of(status));

        UserDto dto = UserMapper.toDto(user);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.firstName()).isEqualTo("Jane");
        assertThat(dto.lastName()).isEqualTo("Doe");
        assertThat(dto.oib()).isEqualTo("12345678901");
        assertThat(dto.lastCardStatus().cardStatus()).isEqualTo(CardStatusEnum.APPROVED);
    }

    @Test
    void toDto_fromUserEntity_shouldReturnNullStatus_whenNoCardStatuses() {
        UserEntity entity = new UserEntity();
        entity.setId(2);
        entity.setFirstName("John");
        entity.setLastName("Smith");
        entity.setOib("98765432100");
        entity.setCardStatuses(Collections.emptyList());

        UserDto dto = UserMapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.lastCardStatus()).isNull();
    }

    @Test
    void toDto_fromUserCardStatusEntity_shouldMapFieldsCorrectly() {
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOib("12345678901");

        UserCardStatusEntity status = new UserCardStatusEntity();
        status.setUser(user);
        status.setStatus(CardStatusEnum.APPROVED);
        status.setCreatedAt(Instant.now());

        UserDto dto = UserMapper.toDto(status);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.firstName()).isEqualTo("Jane");
        assertThat(dto.lastName()).isEqualTo("Doe");
        assertThat(dto.oib()).isEqualTo("12345678901");
        assertThat(dto.lastCardStatus().cardStatus()).isEqualTo(CardStatusEnum.APPROVED);
    }

    @Test
    void toEntity_shouldReturnNull_whenDtoIsNull() {
        assertThat(UserMapper.toEntity(null)).isNull();
    }

    @Test
    void toEntity_shouldMapFieldsCorrectly() {
        UserDto dto = new UserDto(1, "Ana", "Pavlovic", "11122233344", new CardStatusDto(1, 1, null, CardStatusEnum.REJECTED));
        UserEntity entity = UserMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getFirstName()).isEqualTo("Ana");
        assertThat(entity.getLastName()).isEqualTo("Pavlovic");
        assertThat(entity.getOib()).isEqualTo("11122233344");
        assertThat(entity.getCardStatuses()).isEmpty();
    }
}
