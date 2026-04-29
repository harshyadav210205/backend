package com.cyberincident.backend.SecurityMonitor.repository;


import com.cyberincident.backend.SecurityMonitor.model.LoginAttempt;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LoginAttemptRepository extends MongoRepository<LoginAttempt, String> {

    Optional<LoginAttempt> findByUsername(String username);

}