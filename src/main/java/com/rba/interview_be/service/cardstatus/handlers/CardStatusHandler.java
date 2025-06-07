package com.rba.interview_be.service.cardstatus.handlers;

import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.enums.CardStatusEnum;

public interface CardStatusHandler {

    boolean isResponsibleFor(CardStatusEnum status);

    void handle(UserEntity user, CardStatusEnum status, UserCardStatusEntity result);

}
