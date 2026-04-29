package com.cyberincident.backend.SecurityMonitor.service;



import com.cyberincident.backend.alert.service.AlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ThreatDetectionService {
    private final AlertService alertService;
    private static final int MAX_FAILED_ATTEMPTS = 5;

    public ThreatDetectionService(AlertService alertService) {
        this.alertService = alertService;
    }

    public void detectFailedLogin(String username, int attempts) {

        if(attempts >= MAX_FAILED_ATTEMPTS){

            log.warn("⚠️ SECURITY ALERT: Possible brute force attack on user {}", username);

            alertService.sendAlert(
                    "BRUTE_FORCE",
                    "Multiple failed login attempts for user: " + username
            );
        }
    }

    public void detectDifferentIP(String username, String oldIp, String newIp){

        if(oldIp != null && !oldIp.equals(newIp)){
            log.warn("⚠️ SECURITY ALERT: User {} logged in from different IP {} -> {}",username,oldIp,newIp);
        }
    }
}
