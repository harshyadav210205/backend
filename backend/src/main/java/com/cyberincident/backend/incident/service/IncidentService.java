package com.cyberincident.backend.incident.service;

import com.cyberincident.backend.SecurityMonitor.service.AuditLogService;
import com.cyberincident.backend.SecurityMonitor.service.ThreatAnalyticsService;
import com.cyberincident.backend.incident.dto.CreateIncidentRequest;
import com.cyberincident.backend.incident.model.Incident;
import com.cyberincident.backend.incident.model.IncidentStatus;
import com.cyberincident.backend.incident.model.Severity;
import com.cyberincident.backend.incident.repository.IncidentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final AuditLogService auditLogService;
    private final ThreatAnalyticsService threatAnalyticsService;

    // USER: Create incident
    public Incident createIncident(CreateIncidentRequest request, HttpServletRequest httpRequest) {


        Severity severity = classifySeverity(request.getDescription());



        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Incident incident = Incident.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .severity(severity) // temporary
                .status(IncidentStatus.OPEN)
                .reportedBy(username)
                .createdAt(LocalDateTime.now())
                .build();

        incident.setSeverity(severity);

        auditLogService.log(username,"CREATE_INCIDENT",httpRequest.getRemoteAddr());

        threatAnalyticsService.detectIncidentSpike();


        return incidentRepository.save(incident);
    }

    // USER: Get own incidents
    public List<Incident> getMyIncidents() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return incidentRepository.findByReportedBy(username);
    }

    // ADMIN: Get all incidents
    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public Object updateStatus(String id, IncidentStatus status) {
        Incident incident = incidentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Incident not found"));

        incident.setStatus(status);

        return incidentRepository.save(incident);
    }

    private Severity classifySeverity(String description) {

        description = description.toLowerCase();

        if(description.contains("breach") || description.contains("data leak"))
            return Severity.CRITICAL;

        if(description.contains("phishing") || description.contains("malware"))
            return Severity.HIGH;

        if(description.contains("suspicious login"))
            return Severity.MEDIUM;

        return Severity.LOW;
    }


}
