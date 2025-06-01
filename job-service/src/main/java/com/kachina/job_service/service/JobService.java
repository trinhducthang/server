package com.kachina.job_service.service;

import com.kachina.job_service.dto.request.JobFilterRequest;
import com.kachina.job_service.dto.request.JobRequest;
import com.kachina.job_service.dto.response.ApiResponse;
import com.kachina.job_service.dto.response.CompanyResponse;
import com.kachina.job_service.dto.response.JobResponse;
import com.kachina.job_service.entity.Job;
import com.kachina.job_service.exception.AppException;
import com.kachina.job_service.exception.NotFoundException;
import com.kachina.job_service.helper.AuthHelper;
import com.kachina.job_service.mapper.JobMapper;
import com.kachina.job_service.repository.JobRepository;
import com.kachina.job_service.repository.httpClient.CompanyClient;
import com.kachina.job_service.repository.httpClient.RecruitmentDetailsClient;
import com.kachina.job_service.specification.JobSpecification;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final AuthHelper authHelper;
    private final JobMapper jobMapper;
    private final CompanyClient companyClient;
    private final JobSpecification jobSpecification;
    private final RecruitmentDetailsClient rdsClient;

    public ResponseEntity<ApiResponse<JobResponse>> addJob(JobRequest request) {
        String authorId = authHelper.getCurrentUserId();
        Job job = jobMapper.toJob(request, authorId);
        job = jobRepository.save(job);

        ApiResponse<JobResponse> response = ApiResponse.<JobResponse>builder()
                .message("Tạo tin tuyển dụng thành công!")
                .status(201)
                .result(jobMapper.toJobResponse(job, null))
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<JobResponse>> updateJob(String jobId, JobRequest request) {
        Optional<Job> jobOpt = jobRepository.findById(jobId);
        if(!jobOpt.isPresent()) {
            throw new NotFoundException("Không tìm thấy tin tuyển dụng!");
        }

        Job job = jobMapper.toJob(jobOpt.get(), request);
        job = jobRepository.save(job);

        ApiResponse<JobResponse> response = ApiResponse.<JobResponse>builder()
                .message("Cập nhật tin tuyển dụng thành công!")
                .status(200)
                .result(jobMapper.toJobResponse(job, null))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<JobResponse>> getJobByIdWithoutCompany(String jobId) {
        Optional<Job> jobOpt = jobRepository.findById(jobId);
        if(!jobOpt.isPresent()) {
            throw new NotFoundException("Không tìm thấy tin tuyển dụng!");
        }

        ApiResponse<JobResponse> response = ApiResponse.<JobResponse>builder()
                .status(200)
                .result(jobMapper.toJobResponse(jobOpt.get(), null))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<JobResponse>> getJobByIdWithCompany(String jobId) {
        Optional<Job> jobOpt = jobRepository.findById(jobId);
        if(!jobOpt.isPresent()) {
            throw new NotFoundException("Không tìm thấy tin tuyển dụng!");
        }

        CompanyResponse company;
        try {
            ApiResponse<CompanyResponse> result = companyClient.getCompanyByAuthor(jobOpt.get().getAuthorId());

            if (result == null || result.getResult() == null) {
                throw new NotFoundException("Không tìm thấy thông tin công ty!");
            }

            company = result.getResult();
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Không tìm thấy thông tin công ty!");
        } catch (FeignException e) {
            throw new RuntimeException("Lỗi khi kết nối đến Company Service: " + e.getMessage(), e);
        }

        ApiResponse<JobResponse> response = ApiResponse.<JobResponse>builder()
                .status(200)
                .result(jobMapper.toJobResponse(jobOpt.get(), company))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<JobResponse>> toggleEnableJob(String jobId) {
        Optional<Job> jobOpt = jobRepository.findById(jobId);
        if(!jobOpt.isPresent()) {
            throw new NotFoundException("Không tìm thấy tin tuyển dụng!");
        }

        Job job = jobOpt.get();
        job.setEnabled(!job.getEnabled());

        job = jobRepository.save(job);

        ApiResponse<JobResponse> response = ApiResponse.<JobResponse>builder()
                .message("Cập nhật tin tuyển dụng thành công!")
                .status(200)
                .result(jobMapper.toJobResponse(job, null))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Boolean>> deleteJob(String jobId) {
        Optional<Job> jobOpt = jobRepository.findById(jobId);
        if(!jobOpt.isPresent()) {
            throw new NotFoundException("Không tìm thấy tin tuyển dụng!");
        }

        Job job = jobOpt.get();
        job.setDeleted(true);

        try {
            rdsClient.deleteByJobId(job.getId());
        } catch (Exception e) {
            throw new AppException("Có lỗi trong quá trình xử lý!");
        }

        jobRepository.save(job);

        ApiResponse<Boolean> response = ApiResponse.<Boolean>builder()
                .message("Xóa tin tuyển dụng thành công!")
                .status(200)
                .result(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Map<String, Object>>> getJobsByAuthor(String search, Pageable pageable) {
        String authorId = authHelper.getCurrentUserId();
        Page<Job> pageResult = jobRepository.findByAuthorIdAndDeletedFalse(authorId, pageable);

        Map<String, Object> result = new HashMap<>();
        result.put("jobs", pageResult.getContent().stream().map(item -> jobMapper.toJobResponse(item, null)).collect(Collectors.toList()));
        result.put("currentPage", pageResult.getNumber());
        result.put("totalItems", pageResult.getTotalElements());
        result.put("totalPages", pageResult.getTotalPages());
        result.put("hasNext", pageResult.hasNext());
        result.put("hasPrevious", pageResult.hasPrevious());

        ApiResponse<Map<String, Object>> response = ApiResponse.<Map<String, Object>>builder()
                .status(200)
                .result(result)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Map<String, Object>>> searchJob(JobFilterRequest filter, Pageable pageable) {
        List<String> authorIds = null;
        if(filter.getCompany_field() != null && filter.getCompany_field() != 0)
            authorIds = companyClient.getAuthorIdsByCompanyFields(filter.getCompany_field()).getResult();

        Specification<Job> spec = jobSpecification.getFilteredJobs(filter, authorIds);
        Page<Job> pageResult = jobRepository.findAll(spec, pageable);

        List<JobResponse> jobs = jobMapper.toListJobResponse(pageResult.getContent(), companyClient);

        Map<String, Object> result = new HashMap<>();
        result.put("jobs", jobs);
        result.put("currentPage", pageResult.getNumber());
        result.put("totalItems", pageResult.getTotalElements());
        result.put("totalPages", pageResult.getTotalPages());
        result.put("hasNext", pageResult.hasNext());
        result.put("hasPrevious", pageResult.hasPrevious());

        ApiResponse<Map<String, Object>> response = ApiResponse.<Map<String, Object>>builder()
                .status(200)
                .result(result)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<List<JobResponse>>> getJobsByIds(List<String> ids) {
        List<Job> jobs = jobRepository.findByIdIn(ids);
        List<JobResponse> jobResponses = jobMapper.toListJobResponse(jobs, companyClient);
        ApiResponse<List<JobResponse>> res = ApiResponse.<List<JobResponse>>builder()
                .status(200)
                .result(jobResponses)
                .build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
