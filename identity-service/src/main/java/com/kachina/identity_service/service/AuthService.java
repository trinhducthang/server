package com.kachina.identity_service.service;

import com.kachina.identity_service.dto.response.ApiResponse;
import org.springframework.stereotype.Service;

import com.kachina.identity_service.dto.request.VerifyTokenRequest;
import com.kachina.identity_service.dto.response.VerifyTokenResponse;
import com.kachina.identity_service.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;

    public ApiResponse<VerifyTokenResponse> verifyToken(VerifyTokenRequest verifyTokenRequest) {
        var token = verifyTokenRequest.getToken();
        VerifyTokenResponse body = VerifyTokenResponse.builder()
            .valid(jwtUtils.validateJwtToken(token))
            .userId(jwtUtils.getUserId(token))
        .build();

        return ApiResponse.<VerifyTokenResponse>builder()
                .status(200)
                .result(body)
                .message("Verify token")
                .build();
    }

}
