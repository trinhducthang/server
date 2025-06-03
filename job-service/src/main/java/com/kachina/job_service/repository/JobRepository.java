package com.kachina.job_service.repository;

import com.kachina.job_service.dto.JobProjection;
import com.kachina.job_service.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, String>, JpaSpecificationExecutor<Job> {

    Page<Job> findByAuthorIdAndDeletedFalse(String authorId, Pageable pageable);

    List<Job> findByIdIn(List<String> ids);

    List<JobProjection> findByAuthorIdAndDeletedFalseOrderByUpdatedAt(String authorId);

}
