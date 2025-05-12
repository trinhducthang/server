package com.kachina.api_gateway.service;

import com.kachina.api_gateway.dto.request.VerifyTokenRequest;
import com.kachina.api_gateway.dto.response.ApiResponse;
import com.kachina.api_gateway.dto.response.VerifyTokenResponse;
import com.kachina.api_gateway.repository.IdentityClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class IdentityService {

    private final IdentityClient identityClient;

    public Mono<ApiResponse<VerifyTokenResponse>> verifyToken(String token) {
        return identityClient.verifyToken(new VerifyTokenRequest(token));
    }

}
