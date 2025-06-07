package com.rba.interview_be.unit.mapper;

import com.rba.interview_be.controller.dto.CardStatusDto;
import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.enums.CardStatusEnum;
import com.rba.interview_be.mapper.CardStatusMapper;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class CardStatusMapperTest {

    @Test
    void toDto__shouldMapFieldsCorrectly() {
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOib("12345678901");

        UserCardStatusEntity status = new UserCardStatusEntity();
        status.setId(1);
        status.setUser(user);
        status.setStatus(CardStatusEnum.APPROVED);
        status.setCreatedAt(Instant.now());

        CardStatusDto dto = CardStatusMapper.toDto(status);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(1);
        assertThat(dto.userId()).isEqualTo(1);
        assertThat(dto.cardStatus()).isEqualTo(CardStatusEnum.APPROVED);
    }
}
