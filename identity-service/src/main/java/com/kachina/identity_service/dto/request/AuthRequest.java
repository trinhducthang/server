package com.kachina.identity_service.dto.request;

import lombok.*;

@Data
@Builder
public class AuthRequest {
    private String email;
    private String password;
}
