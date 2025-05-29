package com.kachina.profile_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProfileResponse {
    private String id;
    private String name;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private Date date_of_birth;
    private String position;
    private String career;
    private String introduction;
    private String skills;
    private String exp;
    private String education;
    private String achievement;
    private String language;
    private String hobbies;
    private String other;
    private String tags;
    private Date created_at;
    private Date updated_at;
}
