package com.cyberincident.backend.incident.repository;

import com.cyberincident.backend.incident.model.Incident;
import com.cyberincident.backend.incident.model.Severity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IncidentRepository  extends MongoRepository<Incident, String> {
    List<Incident> findByReportedBy(String username);

    long countByCreatedAtAfter(LocalDateTime oneMinuteAgo);

    long countBySeverity(Severity severity);
}
