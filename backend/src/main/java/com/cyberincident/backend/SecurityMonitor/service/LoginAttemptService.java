package com.cyberincident.backend.SecurityMonitor.service;

import com.cyberincident.backend.SecurityMonitor.model.LoginAttempt;
import com.cyberincident.backend.SecurityMonitor.repository.LoginAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    private final LoginAttemptRepository loginAttemptRepository;
    private final ThreatDetectionService threatDetectionService;

    public void loginFailed(String username, String ip) {

        LoginAttempt attempt = loginAttemptRepository
                .findByUsername(username)
                .orElse(LoginAttempt.builder()
                        .username(username)
                        .ipAddress(ip)
                        .attempts(0)
                        .build());

        attempt.setAttempts(attempt.getAttempts() + 1);
        attempt.setLastAttempt(LocalDateTime.now());

        loginAttemptRepository.save(attempt);
        threatDetectionService.detectFailedLogin(username, attempt.getAttempts());


    }
    public void resetAttempts(String username){

        loginAttemptRepository.findByUsername(username)
                .ifPresent(loginAttemptRepository::delete);
    }

}