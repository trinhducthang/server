package com.kachina.profile_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "candidate-profiles")
@Builder
public class CandidateProfile {

    @Id
    @Column(name = "cp_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "cp_name")
    private String name;
    @Column(name = "cp_fullname")
    private String fullname;
    @Column(name = "cp_email")
    private String email;
    @Column(name = "cp_phone")
    private String phone;
    @Column(name = "cp_address")
    private String address;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "cp_date_of_birth")
    private Date dateOfBirth;
    @Column(name = "cp_position", columnDefinition = "TEXT")
    private String position;
    @Column(name = "cp_career", columnDefinition = "TEXT")
    private String career;
    @Column(name = "cp_introduction", columnDefinition = "TEXT")
    private String introduction;
    @Column(name = "cp_skills", columnDefinition = "TEXT")
    private String skills;
    @Column(name = "cp_exp", columnDefinition = "TEXT")
    private String exp;
    @Column(name = "cp_education", columnDefinition = "TEXT")
    private String education;
    @Column(name = "cp_achievement", columnDefinition = "TEXT")
    private String achievement;
    @Column(name = "cp_language", columnDefinition = "TEXT")
    private String language;
    @Column(name = "cp_hobbies", columnDefinition = "TEXT")
    private String hobbies;
    @Column(name = "cp_other", columnDefinition = "TEXT")
    private String other;
    @Column(name = "cp_tags")
    private String tags;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "cp_created_date")
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "cp_updated_date")
    private Date updatedAt;
    @Column(name = "cp_author_id")
    private String authorId;
    @Column(name = "cp_deleted")
    private Boolean deleted;
    @Column(name = "cp_enabled")
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
