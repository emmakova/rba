package com.rba.interview_be.utils;

import com.rba.interview_be.entities.UserCardStatusEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class UserUtils {

    private UserUtils() {}

    public static String extractLastCardStatus(List<UserCardStatusEntity> cardStatuses) {
        if(CollectionUtils.isEmpty(cardStatuses))
            return null;
        return cardStatuses.getFirst().getStatus();
    }

}
