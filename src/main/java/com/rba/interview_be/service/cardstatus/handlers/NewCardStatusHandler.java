package com.rba.interview_be.service.cardstatus.handlers;

import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.enums.CardStatusEnum;
import com.rba.interview_be.service.apiclients.NewCardRequestApiClient;
import com.rba.interview_be.service.cardstatus.UserCardStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.rba.interview_be.utils.UserCardStatusUtils.cloneStatus;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewCardStatusHandler implements CardStatusHandler{

    private final UserCardStatusService userCardStatusService;
    private final NewCardRequestApiClient newCardRequestApiClient;


    @Override
    public boolean isResponsibleFor(CardStatusEnum status) {
        return CardStatusEnum.REQUEST_NEW.equals(status);
    }

    @Override
    public void handle(UserEntity user, CardStatusEnum status, UserCardStatusEntity result) {
        log.info("Handling specifics for status {} on user {}", status, user.getId());
        try {
            newCardRequestApiClient.submitNewCardRequestForUser(user);
            UserCardStatusEntity newCardStatusEntity = userCardStatusService.createStatus(user, CardStatusEnum.IN_PROGRESS);
            cloneStatus(newCardStatusEntity, result);
        } catch (Exception ex){
            log.info("There was an error when handling specifics for status {} on user {}", status, user.getId());
            log.error(ex.getMessage(), ex);
            UserCardStatusEntity newCardStatusEntity = userCardStatusService.createStatus(user, CardStatusEnum.FAILED);
            cloneStatus(newCardStatusEntity, result);
        }

        log.info("Finished handling specifics for status {} on user {}", status, user.getId());
    }

}
