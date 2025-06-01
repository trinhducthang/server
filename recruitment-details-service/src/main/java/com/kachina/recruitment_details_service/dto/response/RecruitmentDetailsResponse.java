package com.kachina.recruitment_details_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecruitmentDetailsResponse {

    private String id;
    private String feedback;
    private Date application_date;
    private Short status;
    private boolean viewed;

}
