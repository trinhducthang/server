package com.kachina.job_service.repository.httpClient;

import com.kachina.job_service.config.AuthenticationRequestInterceptor;
import com.kachina.job_service.dto.response.ApiResponse;
import com.kachina.job_service.dto.response.CompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "company-service",
        path = "/api/company",
        configuration = {AuthenticationRequestInterceptor.class}
)
public interface CompanyClient {

    @GetMapping("/by-author/{id}")
    ApiResponse<CompanyResponse> getCompanyByAuthor(@PathVariable("id") String authorId);

    @GetMapping("/by-field/{field}")
    ApiResponse<List<String>> getAuthorIdsByCompanyFields(@PathVariable("field") Short field);

    @PostMapping("/by-author-ids")
    ApiResponse<List<CompanyResponse>> getCompaniesByAuthorIds(@RequestBody List<String> authorIds);

}
