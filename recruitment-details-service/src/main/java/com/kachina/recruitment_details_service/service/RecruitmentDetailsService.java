package com.kachina.recruitment_details_service.service;

import com.kachina.recruitment_details_service.dto.request.ApplicantRequest;
import com.kachina.recruitment_details_service.dto.request.ApplyRequest;
import com.kachina.recruitment_details_service.dto.request.FeedbackRequest;
import com.kachina.recruitment_details_service.dto.response.*;
import com.kachina.recruitment_details_service.entity.RecruitmentDetails;
import com.kachina.recruitment_details_service.exception.NotFoundException;
import com.kachina.recruitment_details_service.helper.AuthHelper;
import com.kachina.recruitment_details_service.mapper.RecruitmentDetailsMapper;
import com.kachina.recruitment_details_service.repository.RecruitmentDetailsRepository;
import com.kachina.recruitment_details_service.repository.httpClient.JobClient;
import com.kachina.recruitment_details_service.repository.httpClient.ProfileClient;
import lombok.RequiredArgsConstructor;
import net.htmlparser.jericho.CharacterReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitmentDetailsService {

    private final RecruitmentDetailsRepository repository;
    private final RecruitmentDetailsMapper mapper;
    private final AuthHelper authHelper;
    private final JobClient jobClient;
    private final ProfileClient profileClient;

    public ResponseEntity<ApiResponse<Boolean>> apply(ApplyRequest request) {
        String authorId = authHelper.getCurrentUserId();
        RecruitmentDetails recruitmentDetails = null;
        Optional<RecruitmentDetails> rdOpt = repository.findByAuthorIdAndJobId(authorId, request.getJob_id());
        if(rdOpt.isPresent()) {
            recruitmentDetails = rdOpt.get();
        } else {
            recruitmentDetails = new RecruitmentDetails();
            recruitmentDetails.setAuthorId(authorId);
            recruitmentDetails.setJobId(request.getJob_id());
            JobResponse jobResponse = jobClient.getJobWithCompany(request.getJob_id()).getResult();
            recruitmentDetails.setEmployerId(jobResponse.getAuthor_id());
        }
        recruitmentDetails.setProfileId(request.getProfile_id());
        recruitmentDetails.setFeedback("");
        recruitmentDetails.setStatus((short) 0);
        recruitmentDetails.setViewed(false);
        recruitmentDetails.setApplicationDate(new Date());
        repository.save(recruitmentDetails);
        ApiResponse<Boolean> res = ApiResponse.<Boolean>builder()
                .status(HttpStatus.CREATED.value())
                .message("Ứng tuyển thành công!")
                .result(true)
                .build();
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<Map<String, Object>>> getAppliedJobs(Pageable pageable) {
        String authorId = authHelper.getCurrentUserId();
        Page<RecruitmentDetails> pageResult = repository.findByAuthorId(authorId, pageable);

        List<String> jobIds = pageResult.getContent()
                .stream()
                .map(item -> item.getJobId())
                .collect(Collectors.toList());

        ApiResponse<List<JobResponse>> jobResponses = jobClient.getJobsByIds(jobIds);

        Map<String, JobResponse> mapJobResponses = jobResponses.getResult()
                .stream()
                .collect(Collectors.toMap(JobResponse::getId, j -> j));

        Map<String, Object> result = new HashMap<>();
        result.put("appliedJobs", pageResult.getContent()
                .stream()
                .map(item ->
                    AppliedJobResponse.builder()
                        .job(mapJobResponses.get(item.getJobId()))
                        .recruitmentDetails(mapper.toRecruitmentDetailsResponse(item))
                    .build()).collect(Collectors.toList()));
        result.put("currentPage", pageResult.getNumber());
        result.put("totalItems", pageResult.getTotalElements());
        result.put("totalPages", pageResult.getTotalPages());
        result.put("hasNext", pageResult.hasNext());
        result.put("hasPrevious", pageResult.hasPrevious());

        ApiResponse<Map<String, Object>> res = ApiResponse.<Map<String, Object>>builder()
                .result(result)
                .status(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Boolean>> isAppliedByJobId(String jobId) {
        String authorId = authHelper.getCurrentUserId();
        Optional<RecruitmentDetails> rdOpt = repository.findByAuthorIdAndJobId(authorId, jobId);
        ApiResponse<Boolean> res = ApiResponse.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .message(rdOpt.isPresent() ? "Đã ứng tuyển" : "Chưa ứng tuyển")
                .result(rdOpt.isPresent())
                .build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Boolean>> deleteByProfileId(String profileId) {
        Optional<RecruitmentDetails> rdOpt = repository.findByProfileId(profileId);
        if(rdOpt.isPresent()) repository.delete(rdOpt.get());
        ApiResponse<Boolean> res = ApiResponse.<Boolean>builder()
                .message("Xóa thành công!")
                .result(true)
                .status(HttpStatus.NO_CONTENT.value())
                .build();
        return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ApiResponse<Boolean>> deleteByJobId(String jobId) {
        Optional<RecruitmentDetails> rdOpt = repository.findByJobId(jobId);
        if(rdOpt.isPresent()) repository.delete(rdOpt.get());
        ApiResponse<Boolean> res = ApiResponse.<Boolean>builder()
                .message("Xóa thành công!")
                .result(true)
                .status(HttpStatus.NO_CONTENT.value())
                .build();
        return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllProfiles(String jobId, Pageable pageable) {
        Page<RecruitmentDetails> pageResult = repository.findByJobIdContainingIgnoreCase(jobId, pageable);
        List<String> profileIds = pageResult.getContent().stream().map(item -> item.getProfileId()).collect(Collectors.toList());
        ApiResponse<List<ProfileResponse>> apiResponse = profileClient.getByIds(new ApplicantRequest(profileIds));
        Map<String, ProfileResponse> mapProfile = apiResponse.getResult()
                .stream()
                .collect(Collectors.toMap(ProfileResponse::getId, p -> p));

        Map<String, Object> result = new HashMap<>();
        result.put("profiles", pageResult.getContent()
                .stream()
                .map(item ->
                        ApplicantResponse.builder()
                                .profileResponse(mapProfile.get(item.getProfileId()))
                                .recruitmentDetailsResponse(mapper.toRecruitmentDetailsResponse(item))
                                .build()).collect(Collectors.toList()));
        result.put("currentPage", pageResult.getNumber());
        result.put("totalItems", pageResult.getTotalElements());
        result.put("totalPages", pageResult.getTotalPages());
        result.put("hasNext", pageResult.hasNext());
        result.put("hasPrevious", pageResult.hasPrevious());

        ApiResponse<Map<String, Object>> res = ApiResponse.<Map<String, Object>>builder()
                .result(result)
                .status(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Boolean>> updateStatus(String id, Short status, FeedbackRequest request) {
        Optional<RecruitmentDetails> rdsOpt = repository.findById(id);
        if(!rdsOpt.isPresent()) throw new NotFoundException("Không tồn tại đơn ứng tuyển!");
        RecruitmentDetails rds = rdsOpt.get();
        rds.setStatus(status);
        if(!request.getFeedback().isBlank()) rds.setFeedback(CharacterReference.encode(request.getFeedback()));
        repository.save(rds);
        ApiResponse<Boolean> res = ApiResponse.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật thông tin thành công!")
                .result(true)
                .build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Boolean>> setViewed(String id) {
        Optional<RecruitmentDetails> rdsOpt = repository.findByProfileId(id);
        ApiResponse<Boolean> res = null;
        if(!rdsOpt.isPresent()) {
            res = ApiResponse.<Boolean>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Cập nhật thông tin không thành công!")
                    .result(false)
                    .build();
        } else {
            RecruitmentDetails rds = rdsOpt.get();
            if(!rds.isViewed()) {
                rds.setViewed(true);
                repository.save(rds);
            }
            res = ApiResponse.<Boolean>builder()
                    .status(HttpStatus.OK.value())
                    .message("Cập nhật thông tin thành công!")
                    .result(true)
                    .build();
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
