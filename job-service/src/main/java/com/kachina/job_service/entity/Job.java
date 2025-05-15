package com.kachina.job_service.entity;

import com.kachina.job_service.dto.response.CompanyResponse;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "jobs")
public class Job {

    @Id
    @Column(name = "job_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "job_title")
    private String title;
    @Column(name = "job_description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "job_benefit", columnDefinition = "TEXT")
    private String benefit;
    @Column(name = "job_requirement", columnDefinition = "TEXT")
    private String requirement;
    @Column(name = "job_location")
    private String location;
    @Column(name = "job_location_details")
    private String locationDetails;
    @Column(name = "job_salary")
    private double salary;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "company_created_date")
    private Date deadline;
    @Column(name = "job_exp")
    private Short exp;
    @Column(name = "job_form_of_work")
    private Short formOfWork;
    @Column(name = "job_gender")
    private Short gender;
    @Column(name = "job_field")
    private Short jobField;
    @Column(name = "job_number_of_recruits")
    private int numberOfRecruits;
    @Column(name = "job_rank")
    private Short rank;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "company_created_date")
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "company_created_date")
    private Date updatedAt;
    @Column(name = "job_author_id")
    private String authorId;
    @Column(name = "job_deleted")
    private Boolean deleted;
    @Column(name = "job_enabled")
    private Boolean enabled;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.enabled = true;
        this.deleted = false;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

}
