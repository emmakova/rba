package com.rba.interview_be.utils;

import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class UserUtils {

    private UserUtils() {
    }

    public static UserCardStatusEntity extractLastCardStatus(List<UserCardStatusEntity> cardStatuses) {
        if (CollectionUtils.isEmpty(cardStatuses))
            return null;
        return cardStatuses.getFirst();
    }

    public static void addCardStatusToUser(UserEntity userEntity, UserCardStatusEntity status){
        userEntity.getCardStatuses().add(status);
    }

}
