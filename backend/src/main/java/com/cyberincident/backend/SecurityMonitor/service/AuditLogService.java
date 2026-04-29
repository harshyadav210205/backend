package com.cyberincident.backend.SecurityMonitor.service;

import com.cyberincident.backend.SecurityMonitor.model.AuditLog;
import com.cyberincident.backend.SecurityMonitor.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void log(String username, String action, String ipAddress) {

        AuditLog log = AuditLog.builder()
                .username(username)
                .action(action)
                .ipAddress(ipAddress)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }
}