package com.kachina.job_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompanyResponse {
    private String id;
    private String name;
    private String address;
    private String description;
    private Short business_type;
    private String email;
    private List<Short> fields;
    private String logo_url;
    private String phone;
    private String size;
    private String tax_code;
    private String website;
    private String cover_photo;
    private String authorId;
}
