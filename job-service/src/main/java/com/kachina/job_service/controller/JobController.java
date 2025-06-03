package com.kachina.job_service.controller;

import com.kachina.job_service.dto.request.JobFilterRequest;
import com.kachina.job_service.dto.request.JobRequest;
import com.kachina.job_service.dto.response.ApiResponse;
import com.kachina.job_service.dto.response.JobResponse;
import com.kachina.job_service.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<JobResponse>> addCompany(@RequestBody JobRequest request) {
        return jobService.addJob(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> addCompany(@PathVariable("id") String jobId, @RequestBody JobRequest request) {
        return jobService.updateJob(jobId, request);
    }

    @GetMapping("/without-company/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> getJobWithoutCompany(@PathVariable("id") String id) {
        return jobService.getJobByIdWithoutCompany(id);
    }

    @GetMapping("/with-company/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> getJobWithCompany(@PathVariable("id") String id) {
        return jobService.getJobByIdWithCompany(id);
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> toggleEnable(@PathVariable("id") String id) {
        return jobService.toggleEnableJob(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteJob(@PathVariable("id") String id) {
        return jobService.deleteJob(id);
    }

    @GetMapping("/my-jobs")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getListJob(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return jobService.getJobsByAuthor(search, pageable);
    }

    @PostMapping("enable-jobs")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getListJob(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestBody JobFilterRequest request
    ) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return jobService.searchJob(request, pageable);
    }

    @PostMapping("/by-ids")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getJobsByIds(@RequestBody List<String> jobIds) {
        return jobService.getJobsByIds(jobIds);
    }

    @GetMapping("/all-jobs")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getAllJobs() {
        return jobService.getAllJobs();
    }

}
