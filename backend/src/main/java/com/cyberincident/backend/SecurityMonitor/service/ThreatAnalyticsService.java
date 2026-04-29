package com.cyberincident.backend.SecurityMonitor.service;


import com.cyberincident.backend.alert.service.AlertService;
import com.cyberincident.backend.incident.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThreatAnalyticsService {
    private final AlertService alertService;
    private final IncidentRepository incidentRepository;

    private static final int INCIDENT_THRESHOLD = 10;

    public void detectIncidentSpike(){

        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);

        long count = incidentRepository.countByCreatedAtAfter(oneMinuteAgo);

        if(count >= INCIDENT_THRESHOLD){

            log.warn("🚨 Incident spike detected");

            alertService.sendAlert(
                    "INCIDENT_SPIKE",
                    "High number of incidents detected in last minute"
            );
        }
    }

}