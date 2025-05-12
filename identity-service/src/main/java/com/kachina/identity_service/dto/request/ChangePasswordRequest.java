package com.kachina.identity_service.dto.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String current_password;
    private String new_password;
    private String confirm_password;

}
