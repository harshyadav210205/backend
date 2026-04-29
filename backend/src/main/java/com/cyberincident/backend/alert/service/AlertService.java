package com.cyberincident.backend.alert.service;


import com.cyberincident.backend.alert.model.SecurityAlert;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendAlert(String type, String message){

        SecurityAlert alert = SecurityAlert.builder()
                .type(type)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        messagingTemplate.convertAndSend("/topic/security-alerts", alert);
    }
}