package com.cyberincident.backend.SecurityMonitor.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "login_attempts")
public class LoginAttempt {

    @Id
    private String id;

    private String username;

    private String ipAddress;

    private int attempts;

    private LocalDateTime lastAttempt;
}