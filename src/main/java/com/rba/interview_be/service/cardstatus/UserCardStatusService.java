package com.rba.interview_be.service.cardstatus;

import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.enums.CardStatusEnum;

public interface UserCardStatusService {

    UserCardStatusEntity createStatus(UserEntity user, CardStatusEnum status);

    void deleteAllForUser(UserEntity user);
}
