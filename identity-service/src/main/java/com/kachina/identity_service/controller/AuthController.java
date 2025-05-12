package com.kachina.identity_service.controller;

import com.kachina.identity_service.dto.request.*;
import com.kachina.identity_service.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kachina.identity_service.service.AuthService;
import com.kachina.identity_service.service.UserService;
import com.kachina.identity_service.dto.response.AuthResponse;
import com.kachina.identity_service.dto.response.UserResponse;
import com.kachina.identity_service.dto.response.VerifyTokenResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(userService.getUser(authRequest), HttpStatus.OK);
    }

    @PostMapping("/sign-up/candidate")
    public ResponseEntity<ApiResponse<UserResponse>> signup(@RequestBody CandidateCreationRequest request) {
        return new ResponseEntity<>(userService.createCandidate(request), HttpStatus.CREATED);
    }

    @PostMapping("/sign-up/employer")
    public ResponseEntity<ApiResponse<UserResponse>> signup(@RequestBody EmployerCreationRequest request) {
        return new ResponseEntity<>(userService.createEmployer(request), HttpStatus.CREATED);
    }

    @PostMapping("/verify-token")
    public ResponseEntity<ApiResponse<VerifyTokenResponse>> verifyToken(@RequestBody VerifyTokenRequest verifyTokenRequest) {
        return new ResponseEntity<>(authService.verifyToken(verifyTokenRequest), HttpStatus.OK);
    }

}