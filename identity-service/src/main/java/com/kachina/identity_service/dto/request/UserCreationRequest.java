package com.kachina.identity_service.dto.request;

import java.time.*;

import lombok.*;

@Data
@Builder
public class UserCreationRequest {
    
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private LocalDate dob;

}
