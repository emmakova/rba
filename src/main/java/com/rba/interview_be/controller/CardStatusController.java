package com.rba.interview_be.controller;

import com.rba.interview_be.controller.dto.CardStatusDto;
import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.mapper.CardStatusMapper;
import com.rba.interview_be.service.cardstatus.StatusHandlerService;
import com.rba.interview_be.service.cardstatus.UserCardStatusService;
import com.rba.interview_be.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("card-status")
@RequiredArgsConstructor
public class CardStatusController {

    private final UserService userService;
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

    @GetMapping
    public ResponseEntity<List<CardStatusDto>> getCardStatusesForUser(@RequestParam Integer userId) {
        UserEntity user = userService.findById(userId);
        return ResponseEntity.ok(
                userCardStatusService.findAllStatusesForUser(user)
                        .stream()
                        .map(CardStatusMapper::toDto)
                        .collect(Collectors.toList()));
    }
}
