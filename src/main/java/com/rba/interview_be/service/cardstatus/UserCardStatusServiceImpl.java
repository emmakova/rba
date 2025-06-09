package com.rba.interview_be.service.cardstatus;

import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.enums.CardStatusEnum;
import com.rba.interview_be.exceptions.StatusUpdateException;
import com.rba.interview_be.repository.UserCardStatusRepository;
import com.rba.interview_be.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserCardStatusServiceImpl implements UserCardStatusService {

    private final UserCardStatusRepository userCardStatusRepository;

    @Override
    public List<UserCardStatusEntity> findAllStatusesForUser(UserEntity userEntity) {
        return userCardStatusRepository.findAllByUserIdOrderByCreatedAtDesc(userEntity.getId());
    }

    @Override
    public UserCardStatusEntity createStatus(UserEntity userEntity, CardStatusEnum status) {
        log.info("Creating status {} for user {}", status, userEntity.getId());

        checkIfStatusUpdateIsAllowed(userEntity, status);

        UserCardStatusEntity userCardStatusEntity = new UserCardStatusEntity();
        userCardStatusEntity.setUser(userEntity);
        userCardStatusEntity.setStatus(status);
        userCardStatusEntity.setCreatedAt(Instant.now());
        return userCardStatusRepository.save(userCardStatusEntity);
    }

    @Override
    public void deleteAllForUser(UserEntity userEntity) {
        userCardStatusRepository.deleteAllByUserId(userEntity.getId());
    }

    private void checkIfStatusUpdateIsAllowed(UserEntity userEntity, CardStatusEnum newCardStatus){
        if (lastStatusEqualsNewStatus(userEntity.getCardStatuses(), newCardStatus)) {
            throw new StatusUpdateException("New status cant be the same as last one!");
        }
    }


    private boolean lastStatusEqualsNewStatus(List<UserCardStatusEntity> cardStatuses, CardStatusEnum newCardStatus) {
        if (CollectionUtils.isEmpty(cardStatuses))
            return false;
        return newCardStatus.equals(UserUtils.extractLastCardStatus(cardStatuses).getStatus());
    }
}
