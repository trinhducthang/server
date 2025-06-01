package com.kachina.recruitment_details_service.repository.httpClient;

import com.kachina.recruitment_details_service.config.AuthenticationRequestInterceptor;
import com.kachina.recruitment_details_service.dto.request.ApplicantRequest;
import com.kachina.recruitment_details_service.dto.response.ApiResponse;
import com.kachina.recruitment_details_service.dto.response.ProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "profile-service",
        path = "/profile",
        configuration = {AuthenticationRequestInterceptor.class}
)
public interface ProfileClient {

    @GetMapping("/list-by-ids")
    ApiResponse<List<ProfileResponse>> getByIds(@RequestBody ApplicantRequest request);

}
