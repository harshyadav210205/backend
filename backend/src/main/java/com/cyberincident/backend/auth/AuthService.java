package com.cyberincident.backend.auth;

import com.cyberincident.backend.SecurityMonitor.service.LoginAttemptService;
import com.cyberincident.backend.auth.dto.AuthResponse;
import com.cyberincident.backend.auth.dto.LoginRequest;
import com.cyberincident.backend.auth.dto.RegisterRequest;
import com.cyberincident.backend.auth.model.Role;
import com.cyberincident.backend.auth.model.User;
import com.cyberincident.backend.auth.repository.UserRepository;
import com.cyberincident.backend.auth.security.JwtService;
import com.cyberincident.backend.SecurityMonitor.service.AuditLogService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuditLogService auditLogService;
    private final LoginAttemptService loginAttemptService;

    // REGISTER
    public AuthResponse register(RegisterRequest request ,  HttpServletRequest httpRequest) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(String.valueOf(user));
        String ip = httpRequest.getRemoteAddr();
        auditLogService.log(user.getUsername(),"REGISTER",ip);


        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }

    // LOGIN
    public AuthResponse login(LoginRequest request , HttpServletRequest httpRequest) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            loginAttemptService.loginFailed(
                    request.getUsername(),
                    httpRequest.getRemoteAddr()
            );

            auditLogService.log(request.getUsername(),"FAILED_LOGIN",httpRequest.getRemoteAddr());

            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());

        auditLogService.log(user.getUsername(),"LOGIN_SUCCESS",httpRequest.getRemoteAddr());


        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}
