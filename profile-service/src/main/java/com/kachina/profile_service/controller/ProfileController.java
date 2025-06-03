package com.kachina.profile_service.controller;

import com.kachina.profile_service.dto.request.ApplicantRequest;
import com.kachina.profile_service.dto.request.ProfileRequest;
import com.kachina.profile_service.dto.response.ApiResponse;
import com.kachina.profile_service.dto.response.ProfileResponse;
import com.kachina.profile_service.service.ProfileService;
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
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProfileResponse>> create(@RequestBody ProfileRequest request) {
        return profileService.create(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ProfileResponse>> update(@PathVariable("id") String id, @RequestBody ProfileRequest request) {
        return profileService.update(id, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileResponse>> get(@PathVariable("id") String id) {
        return profileService.get(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable("id") String id) {
        return profileService.delete(id);
    }

    @GetMapping("/my-profiles")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getMyProfile(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sortBy", defaultValue = "updatedAt") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return profileService.get(search, pageable);
    }

    @GetMapping("/my-all-profiles")
    public ResponseEntity<ApiResponse<List<ProfileResponse>>> getMyAllProfile() {
        return profileService.get();
    }

    @PostMapping("/list-by-ids")
    public ResponseEntity<ApiResponse<List<ProfileResponse>>> getByIds(@RequestBody ApplicantRequest request) {
        return profileService.get(request);
    }

}
