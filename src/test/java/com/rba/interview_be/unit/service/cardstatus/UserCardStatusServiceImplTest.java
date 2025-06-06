package com.rba.interview_be.unit.service.cardstatus;

import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.enums.CardStatusEnum;
import com.rba.interview_be.repository.UserCardStatusRepository;
import com.rba.interview_be.service.cardstatus.UserCardStatusServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserCardStatusServiceImplTest {

    @InjectMocks
    private UserCardStatusServiceImpl service;

    @Mock
    private UserCardStatusRepository userCardStatusRepository;


    @Test
    void createNewCardStatusForUser_shouldCreateAndSaveEntity() {
        UserEntity user = new UserEntity();
        user.setId(42);

        CardStatusEnum status = CardStatusEnum.APPROVED;

        ArgumentCaptor<UserCardStatusEntity> captor = ArgumentCaptor.forClass(UserCardStatusEntity.class);

        UserCardStatusEntity savedEntity = new UserCardStatusEntity();
        savedEntity.setUser(user);
        savedEntity.setStatus(status);
        savedEntity.setCreatedAt(Instant.now());

        when(userCardStatusRepository.save(any())).thenReturn(savedEntity);

        UserCardStatusEntity result = service.createNewCardStatusForUser(user, status);

        verify(userCardStatusRepository).save(captor.capture());
        UserCardStatusEntity captured = captor.getValue();

        assertThat(captured.getUser()).isEqualTo(user);
        assertThat(captured.getStatus()).isEqualTo(status);
        assertThat(captured.getCreatedAt()).isNotNull();

        assertThat(result).isEqualTo(savedEntity);
    }

    @Test
    void deleteAllForUser_shouldCallRepositoryWithCorrectUserId() {
        UserEntity user = new UserEntity();
        user.setId(99);

        service.deleteAllForUser(user);

        verify(userCardStatusRepository).deleteAllByUserId(99);
    }

}
