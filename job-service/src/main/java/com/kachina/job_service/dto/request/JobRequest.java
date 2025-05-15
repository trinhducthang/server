package com.kachina.job_service.dto.request;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class JobRequest {

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

}
