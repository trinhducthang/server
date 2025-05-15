package com.kachina.job_service.repository.httpClient;

import com.kachina.job_service.config.AuthenticationRequestInterceptor;
import com.kachina.job_service.dto.response.ApiResponse;
import com.kachina.job_service.dto.response.CompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "company-service",
        path = "/api/company",
        configuration = {AuthenticationRequestInterceptor.class}
)
public interface CompanyClient {

    @GetMapping("/by-author/{id}")
    ApiResponse<CompanyResponse> getCompanyByAuthor(@PathVariable("id") String authorId);

}
