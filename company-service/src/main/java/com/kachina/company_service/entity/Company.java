package com.kachina.company_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "companies")
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "company_id")
    private String id;

    @Column(name = "company_name")
    private String name;

    @Column(name = "company_address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "company_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "company_type")
    private Short businessType;

    @Column(name = "company_email")
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "company_fields", joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "field_id")
    private List<Short> fields;

    @Column(name = "company_logo_url", columnDefinition = "TEXT")
    private String logoUrl;

    @Column(name = "company_phone")
    private String phone;

    @Column(name = "company_size")
    private String size;

    @Column(name = "company_tax_code")
    private String taxCode;

    @Column(name = "company_website")
    private String website;

    @Column(name = "company_cover_photo")
    private String coverPhoto;

    @Column(name = "company_author_id")
    private String authorId;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "company_created_date")
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "company_updated_date")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
