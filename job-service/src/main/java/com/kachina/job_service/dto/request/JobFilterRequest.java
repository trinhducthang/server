package com.kachina.job_service.dto.request;

import lombok.Data;

@Data
public class JobFilterRequest {

    private String search;
    private String location;
    private Short category;
    private Short job_field;
    private Short company_field;
    private Short salary;
    private Short exp;
    private Short rank;
    private Short form_of_work;

}
