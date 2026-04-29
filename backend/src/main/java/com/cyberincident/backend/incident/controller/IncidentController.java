package com.cyberincident.backend.incident.controller;

import com.cyberincident.backend.incident.dto.CreateIncidentRequest;
import com.cyberincident.backend.incident.model.Incident;
import com.cyberincident.backend.incident.model.IncidentStatus;
import com.cyberincident.backend.incident.service.IncidentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;

    // USER
    @PostMapping
    public ResponseEntity<Incident> createIncident(
            @RequestBody CreateIncidentRequest request,
            HttpServletRequest httpRequest){

        return ResponseEntity.ok(
                incidentService.createIncident(request,httpRequest)
        );
    }

    // USER
    @GetMapping("/my")
    public List<Incident> getMyIncidents() {
        return incidentService.getMyIncidents();
    }

    // ADMIN
    @GetMapping("/admin/all")
    public List<Incident> getAllIncidents() {
        return incidentService.getAllIncidents();
    }

    @PutMapping("/admin/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable String id,
            @RequestParam IncidentStatus status) {

        return ResponseEntity.ok(
                incidentService.updateStatus(id, status)
        );
    }
}