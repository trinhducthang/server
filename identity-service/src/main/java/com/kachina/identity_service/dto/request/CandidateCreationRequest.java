package com.kachina.identity_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateCreationRequest {
    private String email;
    private String password;
    private String confirm_password;
    private String full_name;
}
