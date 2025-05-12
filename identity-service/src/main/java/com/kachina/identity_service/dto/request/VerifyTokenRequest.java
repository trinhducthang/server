package com.kachina.identity_service.dto.request;

import lombok.Data;

@Data
public class VerifyTokenRequest {
    private String token;
}
