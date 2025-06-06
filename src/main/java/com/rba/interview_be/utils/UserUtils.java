package com.rba.interview_be.utils;

import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class UserUtils {

    private UserUtils() {
    }

    public static UserCardStatusEntity extractLastCardStatus(List<UserCardStatusEntity> cardStatuses) {
        if (CollectionUtils.isEmpty(cardStatuses))
            return null;
        return cardStatuses.stream()
                .filter(Objects::nonNull).max(Comparator.comparing(UserCardStatusEntity::getCreatedAt))
                .orElse(null);
    }

    public static void addCardStatusToUser(UserEntity userEntity, UserCardStatusEntity status){
        userEntity.getCardStatuses().add(status);
    }

}
