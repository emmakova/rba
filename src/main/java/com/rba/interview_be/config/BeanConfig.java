package com.rba.interview_be.config;

import com.rba.interview_be.service.apiclients.NewCardRequestApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public NewCardRequestApiClient newCardRequestApiClient(@Value("${new-card-request-api.base-url}") String baseUrl){
        return new NewCardRequestApiClient(baseUrl);
    }
}
