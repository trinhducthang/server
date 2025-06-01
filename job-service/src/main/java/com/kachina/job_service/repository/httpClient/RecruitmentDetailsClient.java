package com.kachina.job_service.repository.httpClient;


import com.kachina.job_service.config.AuthenticationRequestInterceptor;
import com.kachina.job_service.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "recruitment-details-service",
        path = "/rds",
        configuration = {AuthenticationRequestInterceptor.class}
)
public interface RecruitmentDetailsClient {

    @DeleteMapping("/delete-by-job/{id}")
    ApiResponse<Boolean> deleteByJobId(@PathVariable("id") String jobId);

}
