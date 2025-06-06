package com.rba.interview_be.service.cardstatus.consumer;

import com.rba.interview_be.enums.CardStatusEnum;
import com.rba.interview_be.repository.UserRepository;
import com.rba.interview_be.service.cardstatus.UserCardStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.kafka.consumer.enabled", havingValue = "true")
public class CardStatusConsumer {

    private final UserRepository userRepository;
    private final UserCardStatusService userCardStatusService;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(CardStatusEvent event) {
        log.info("Received CardStatusEvent: {}", event);
        userRepository.findByOib(event.oib()).ifPresent(u -> userCardStatusService.createNewCardStatusForUser(u, CardStatusEnum.valueOf(event.status())));
    }
}
