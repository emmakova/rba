package com.rba.interview_be.service.cardstatus;

import com.rba.interview_be.controller.dto.CardStatusDto;
import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.enums.CardStatusEnum;
import com.rba.interview_be.service.cardstatus.handlers.CardStatusHandler;
import com.rba.interview_be.service.user.UserService;
import com.rba.interview_be.utils.UserCardStatusUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StatusHandlerService {

    private final UserService userService;
    private final UserCardStatusService userCardStatusService;
    private final List<CardStatusHandler> cardStatusHandlers;


    public UserCardStatusEntity changeStatus(CardStatusDto cardStatusDto) {
        UserEntity user = userService.findById(cardStatusDto.userId());
        return changeStatus(user, cardStatusDto.cardStatus());
    }

    public UserCardStatusEntity changeStatus(UserEntity userEntity, CardStatusEnum status) {

        UserCardStatusEntity createdStatus = userCardStatusService.createStatus(userEntity, status);
        UserCardStatusEntity result = new UserCardStatusEntity();

        getStatusHandler(status).ifPresent(handler -> handler.handle(userEntity, status, result));
        if (result.getId() == null) {
            UserCardStatusUtils.cloneStatus(createdStatus, result);
        }
        return result;
    }


    private Optional<CardStatusHandler> getStatusHandler(CardStatusEnum status) {
        return cardStatusHandlers.stream().filter(h -> h.isResponsibleFor(status)).findFirst();
    }


}
