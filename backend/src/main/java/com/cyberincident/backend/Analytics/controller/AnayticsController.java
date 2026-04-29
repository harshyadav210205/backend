package com.cyberincident.backend.Analytics.controller;



import com.cyberincident.backend.Analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
 class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/high-severity-count")
    public long highSeverity(){

        return analyticsService.getHighSeverityIncidents();
    }

    @GetMapping("/incidents-last-minute")
    public long incidentsLastMinute(){

        return analyticsService.getIncidentsLastMinute();
    }

}