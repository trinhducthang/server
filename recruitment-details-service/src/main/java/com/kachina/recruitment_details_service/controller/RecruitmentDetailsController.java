package com.kachina.recruitment_details_service.controller;

import com.kachina.recruitment_details_service.dto.request.ApplyRequest;
import com.kachina.recruitment_details_service.dto.request.FeedbackRequest;
import com.kachina.recruitment_details_service.dto.response.ApiResponse;
import com.kachina.recruitment_details_service.service.RecruitmentDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RecruitmentDetailsController {

    private final RecruitmentDetailsService service;

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<Boolean>> apply(@RequestBody ApplyRequest request) {
        return service.apply(request);
    }

    @GetMapping("/applied-jobs")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAppliedJobs(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            @RequestParam(name = "sortBy", defaultValue = "applicationDate") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return service.getAppliedJobs(pageable);
    }

    @GetMapping("/profiles")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllProfiles(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            @RequestParam(name = "job", defaultValue = "") String jobId,
            @RequestParam(name = "sortBy", defaultValue = "applicationDate") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return service.getAllProfiles(jobId, pageable);
    }

    @GetMapping("/application-by-job/{jobId}")
    public ResponseEntity<ApiResponse<Boolean>> isAppliedByJobId(@PathVariable("jobId") String jobId) {
        return service.isAppliedByJobId(jobId);
    }

    @DeleteMapping("/delete-by-profile/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteByProfileId(@PathVariable("id") String profileId) {
        return service.deleteByProfileId(profileId);
    }

    @DeleteMapping("/delete-by-job/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteByJobId(@PathVariable("id") String jobId) {
        return service.deleteByJobId(jobId);
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<ApiResponse<Boolean>> reject(@PathVariable("id") String id, @RequestBody FeedbackRequest request) {
        return service.updateStatus(id, (short) 2, request);
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<ApiResponse<Boolean>> reject(@PathVariable("id") String id) {
        return service.updateStatus(id, (short) 1, new FeedbackRequest());
    }

    @PutMapping("/view/{profileId}")
    public ResponseEntity<ApiResponse<Boolean>> view(@PathVariable("profileId") String id) {
        return service.setViewed(id);
    }

}
