package com.rba.interview_be.controller;

import com.rba.interview_be.controller.dto.CardStatusDto;
import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.mapper.CardStatusMapper;
import com.rba.interview_be.service.cardstatus.StatusHandlerService;
import com.rba.interview_be.service.cardstatus.UserCardStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("card-status")
@RequiredArgsConstructor
public class CardStatusController {

    private final StatusHandlerService statusHandlerService;
    private final UserCardStatusService userCardStatusService;

    @PostMapping
    public ResponseEntity<List<CardStatusDto>> createCardStatus(@RequestBody CardStatusDto cardStatusDto) {
        UserCardStatusEntity lastStatus = statusHandlerService.changeStatus(cardStatusDto);
        return ResponseEntity.ok(
                userCardStatusService.findAllStatusesForUser(lastStatus.getUser())
                        .stream()
                        .map(CardStatusMapper::toDto)
                        .collect(Collectors.toList()));

    }
}
