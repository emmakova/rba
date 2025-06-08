package com.rba.interview_be.service.cardstatus;

import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.enums.CardStatusEnum;

import java.util.List;

public interface UserCardStatusService {

    List<UserCardStatusEntity> findAllStatusesForUser(UserEntity userEntity);

    UserCardStatusEntity createStatus(UserEntity user, CardStatusEnum status);

    void deleteAllForUser(UserEntity user);
}
