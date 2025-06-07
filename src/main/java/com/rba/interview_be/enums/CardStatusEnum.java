package com.rba.interview_be.enums;

public enum CardStatusEnum {
    REQUEST_NEW,    // this it the first status that is going to be sent, when a user request a card
    IN_PROGRESS,    // request submitted, waiting for response
    FAILED,         // request failed, never sent
    APPROVED,       // card approved
    REJECTED,       // card rejected
    DELIVERED,      // card delivered
    ACTIVE,         // card is active
    NON_ACTIVE,     // card is not active
    CANCELLED       // card is cancelled
}
