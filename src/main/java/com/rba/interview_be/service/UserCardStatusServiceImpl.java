package com.rba.interview_be.service;

import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.enums.CardStatusEnum;
import com.rba.interview_be.repository.UserCardStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class UserCardStatusServiceImpl implements UserCardStatusService {

    private final UserCardStatusRepository userCardStatusRepository;

    @Override
    public UserCardStatusEntity createNewCardStatusForUser(UserEntity userEntity, CardStatusEnum status) {
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
}
