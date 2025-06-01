package com.kachina.recruitment_details_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Table(name = "recruitment-details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "rd_id")
    private String id;
    @Column(name = "rd_job_id")
    private String jobId;
    @Column(name = "rd_profile_id")
    private String profileId;
    @Column(name = "rd_feedback")
    private String feedback;
    @Column(name = "rd_status")
    private Short status;
    @Column(name = "rd_viewed")
    private boolean viewed;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "rd_application_date")
    private Date applicationDate;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "rd_created_date")
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "rd_updated_date")
    private Date updatedAt;
    @Column(name = "rd_author_id")
    private String authorId;
    @Column(name = "rd_employer_id")
    private String employerId;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

}
