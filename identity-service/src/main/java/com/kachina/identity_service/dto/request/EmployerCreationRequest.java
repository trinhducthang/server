package com.kachina.identity_service.dto.request;

import com.kachina.identity_service.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployerCreationRequest {
    private String email;
    private String password;
    private String confirm_password;
    private String full_name;
    private String phone_number;
    private Gender gender;
    private String company_name;
    private String address;
}
