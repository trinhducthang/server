package com.kachina.job_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class JobResponse {
    private String id;
    private String title;
    private String description;
    private String benefit;
    private String requirement;
    private List<String> location;
    private String location_details;
    private double salary;
    private Date deadline;
    private Short exp;
    private Short form_of_work;
    private Short gender;
    private Short job_field;
    private int number_of_recruits;
    private Short rank;
    private Date created_at;
    private Date updated_at;
    private CompanyResponse company;
    private boolean enable;
    private int number_of_applicants;
}
