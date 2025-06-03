package com.kachina.recruitment_details_service.repository.httpClient;

import com.kachina.recruitment_details_service.config.AuthenticationRequestInterceptor;
import com.kachina.recruitment_details_service.dto.response.ApiResponse;
import com.kachina.recruitment_details_service.dto.response.JobResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "job-service",
        path = "/job",
        configuration = {AuthenticationRequestInterceptor.class}
)
public interface JobClient {

    @PostMapping("/by-ids")
    ApiResponse<List<JobResponse>> getJobsByIds(@RequestBody List<String> jobIds);

    @GetMapping("/with-company/{id}")
    ApiResponse<JobResponse> getJobWithCompany(@PathVariable("id") String id);

}
