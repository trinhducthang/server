package com.kachina.identity_service.dto.response;

import lombok.*;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private UserResponse user;
}
