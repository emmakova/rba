package com.rba.interview_be.service.apiclients;

import com.example.api.NewCardRequestApi;
import com.example.invoker.ApiClient;
import com.example.model.NewCardRequest;
import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.enums.CardStatusEnum;

import java.util.Optional;

import static com.rba.interview_be.utils.UserUtils.extractLastCardStatus;


public class NewCardRequestApiClient {

    private final NewCardRequestApi newCardRequestApi;

    public NewCardRequestApiClient(String baseUrl) {
        ApiClient client = new ApiClient();
        client.setBasePath(baseUrl);
        this.newCardRequestApi = new NewCardRequestApi(client);
    }


    public void submitNewCardRequestForUser(UserEntity userEntity) {

        NewCardRequest newCardRequest = new NewCardRequest();
        newCardRequest
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .oib(userEntity.getOib())
                .status(Optional.ofNullable(extractLastCardStatus(userEntity.getCardStatuses()))
                        .map(UserCardStatusEntity::getStatus)
                        .map(CardStatusEnum::name)
                        .orElse(null));

        newCardRequestApi.apiV1CardRequestPost(newCardRequest);
    }
}
