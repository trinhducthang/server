package com.kachina.identity_service.dto.request;

import com.kachina.identity_service.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdationRequest {
    private String company_name;
    private String full_name;
    private Gender gender;
    private String phone_number;
    private String address;
    private String avatar_url;
}
