package com.kachina.profile_service.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ApplicantRequest {
    private List<String> ids;
}
