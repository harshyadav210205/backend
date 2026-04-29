package com.cyberincident.backend.Analytics.service;


import com.cyberincident.backend.incident.model.Severity;
import com.cyberincident.backend.incident.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final IncidentRepository incidentRepository;

    public long getHighSeverityIncidents(){

        return incidentRepository.countBySeverity(Severity.HIGH);
    }

    public long getIncidentsLastMinute(){

        LocalDateTime time = LocalDateTime.now().minusMinutes(1);

        return incidentRepository.countByCreatedAtAfter(time);
    }

}