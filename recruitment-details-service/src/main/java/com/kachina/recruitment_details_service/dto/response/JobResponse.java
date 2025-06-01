package com.kachina.recruitment_details_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class JobResponse {
    private String id;
    private String title;
    private CompanyResponse company;
    private String author_id;
}
