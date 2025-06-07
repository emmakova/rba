package com.rba.interview_be.unit.utils;

import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.enums.CardStatusEnum;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static com.rba.interview_be.utils.UserUtils.extractLastCardStatus;
import static org.assertj.core.api.Assertions.assertThat;

public class UserUtilsTest {

    @Test
    public void testExtractLastCardStatus_returnsMostRecentStatus() {
        UserCardStatusEntity older = new UserCardStatusEntity();
        older.setStatus(CardStatusEnum.IN_PROGRESS);
        older.setCreatedAt(Instant.now().minusSeconds(90000));

        UserCardStatusEntity newer = new UserCardStatusEntity();
        newer.setStatus(CardStatusEnum.APPROVED);
        newer.setCreatedAt(Instant.now());

        List<UserCardStatusEntity> list = Arrays.asList(older, newer);

        UserCardStatusEntity result = extractLastCardStatus(list);

        assertThat(result).isEqualTo(newer);
        assertThat(result.getStatus()).isEqualTo(CardStatusEnum.APPROVED);
    }

    @Test
    public void testExtractLastCardStatus_emptyList_returnsNull() {
        List<UserCardStatusEntity> emptyList = List.of();
        assertThat(extractLastCardStatus(emptyList)).isNull();
    }

    @Test
    public void testExtractLastCardStatus_nullList_returnsNull() {
        assertThat(extractLastCardStatus(null)).isNull();
    }


}
