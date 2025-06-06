package com.rba.interview_be.integration;

import com.rba.interview_be.enums.CardStatusEnum;
import com.rba.interview_be.service.cardstatus.consumer.CardStatusConsumer;
import com.rba.interview_be.service.cardstatus.consumer.CardStatusEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.kafka.KafkaContainer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles({"test"})
public class CardStatusKafkaIntegrationTest {

    static KafkaContainer kafka;

    static {
        kafka = new KafkaContainer("apache/kafka");
        kafka.start();
    }


    @SpyBean
    private CardStatusConsumer consumer;

    @DynamicPropertySource
    static void registerKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired
    KafkaTemplate<String, CardStatusEvent> kafkaTemplate;

    @Test
    void test_CardStatusConsumer_consume_CardStatusEvent() throws InterruptedException {
        CardStatusEvent event = new CardStatusEvent("56783468123", CardStatusEnum.APPROVED.name());
        kafkaTemplate.send("card-status-topic", event);

        Thread.sleep(1000);

        verify(consumer, times(1)).consume(event);
    }


}
