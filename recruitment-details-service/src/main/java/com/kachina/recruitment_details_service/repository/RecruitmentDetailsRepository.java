package com.kachina.recruitment_details_service.repository;

import com.kachina.recruitment_details_service.entity.RecruitmentDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruitmentDetailsRepository extends JpaRepository<RecruitmentDetails, String> {
    Optional<RecruitmentDetails> findByAuthorIdAndJobId(String authorId, String jobId);
    Optional<RecruitmentDetails> findByJobId(String jobId);
    Optional<RecruitmentDetails> findByProfileId(String profileId);
    Page<RecruitmentDetails> findByAuthorId(String authorId, Pageable pageable);
    Page<RecruitmentDetails> findByJobIdContainingIgnoreCase(String jobId, Pageable pageable);
}
