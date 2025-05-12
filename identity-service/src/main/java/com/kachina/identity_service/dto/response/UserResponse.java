package com.kachina.identity_service.dto.response;

import java.util.*;
import java.time.*;

import com.kachina.identity_service.enums.Gender;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String full_name;
    private String description;
    private String address;
    private String company_name;
    private Date date_of_birth;
    private Date created_at;
    private Date updated_at;
    private String phone_number;
    private List<String> roles;
    private boolean enabled;
    private String avatar_url;
    private Gender gender;
}
