package com.rba.interview_be.controller;

import com.rba.interview_be.controller.dto.CardStatusDto;
import com.rba.interview_be.mapper.CardStatusMapper;
import com.rba.interview_be.service.cardstatus.StatusHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("card-status")
@RequiredArgsConstructor
public class CardStatusController {

    private final StatusHandlerService statusHandlerService;

    @PostMapping
    public ResponseEntity<CardStatusDto> createCardStatus(@RequestBody CardStatusDto cardStatusDto){
        return ResponseEntity.ok(
                CardStatusMapper.toDto(statusHandlerService.changeStatus(cardStatusDto))
        );
    }
}
