package com.cyberincident.backend.auth.controller;

import com.cyberincident.backend.auth.AuthService;
import com.cyberincident.backend.auth.dto.AuthResponse;
import com.cyberincident.backend.auth.dto.LoginRequest;
import com.cyberincident.backend.auth.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletRequest httpRequest
    ) {

        return ResponseEntity.ok(authService.register(request, httpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest
    ) {

        return ResponseEntity.ok(authService.login(request, httpRequest));
    }

}

