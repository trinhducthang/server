package com.kachina.profile_service.service;

import com.kachina.profile_service.dto.request.ApplicantRequest;
import com.kachina.profile_service.dto.request.ProfileRequest;
import com.kachina.profile_service.dto.response.ApiResponse;
import com.kachina.profile_service.dto.response.ProfileResponse;
import com.kachina.profile_service.entity.CandidateProfile;
import com.kachina.profile_service.exception.AppException;
import com.kachina.profile_service.exception.NotFoundException;
import com.kachina.profile_service.helper.AuthHelper;
import com.kachina.profile_service.mapper.ProfileMapper;
import com.kachina.profile_service.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import net.htmlparser.jericho.CharacterReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class ProfileService {

    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;
    private final AuthHelper authHelper;

    public ResponseEntity<ApiResponse<ProfileResponse>> create(ProfileRequest request) {
        String authorId = authHelper.getCurrentUserId();
        CandidateProfile profile = profileMapper.toCandidateProfile(request, authorId);
        profile = profileRepository.save(profile);

        ApiResponse<ProfileResponse> res = ApiResponse.<ProfileResponse>builder()
                .message("Tạo hồ sơ thành công!")
                .status(HttpStatus.CREATED.value())
                .result(profileMapper.toProfileResponse(profile))
                .build();

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<ProfileResponse>> update(String id, ProfileRequest request) {
        String authorId = authHelper.getCurrentUserId();
        Optional<CandidateProfile> profileOpt = profileRepository.findById(id);
        if(!profileOpt.isPresent() || profileOpt.get().getDeleted()) {
            throw new NotFoundException("Hồ sơ không tồn tại!");
        }

        if(!authorId.equals(profileOpt.get().getAuthorId())) {
            throw new AppException("Có lỗi trong quá trình sử lý!");
        }

        CandidateProfile profile = profileOpt.get();
        profile.setName(CharacterReference.encode(request.getName()));
        profile.setFullname(CharacterReference.encode(request.getFullname()));
        profile.setEmail(request.getEmail());
        profile.setPhone(request.getPhone());
        profile.setAddress(CharacterReference.encode(request.getAddress()));
        profile.setDateOfBirth(request.getDate_of_birth());
        profile.setPosition(CharacterReference.encode(request.getPosition()));
        profile.setCareer(CharacterReference.encode(request.getCareer()));
        profile.setIntroduction(CharacterReference.encode(request.getIntroduction()));
        profile.setSkills(CharacterReference.encode(request.getSkills()));
        profile.setExp(CharacterReference.encode(request.getExp()));
        profile.setEducation(CharacterReference.encode(request.getEducation()));
        profile.setAchievement(CharacterReference.encode(request.getAchievement()));
        profile.setLanguage(CharacterReference.encode(request.getLanguage()));
        profile.setHobbies(CharacterReference.encode(request.getHobbies()));
        profile.setOther(CharacterReference.encode(request.getOther()));
        profile.setTags(CharacterReference.encode(request.getTags()));
        profile = profileRepository.save(profile);

        ApiResponse<ProfileResponse> res = ApiResponse.<ProfileResponse>builder()
                .message("Cập nhật hồ sơ thành công!")
                .status(HttpStatus.OK.value())
                .result(profileMapper.toProfileResponse(profile))
                .build();

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Boolean>> delete(String id) {
        String authorId = authHelper.getCurrentUserId();
        Optional<CandidateProfile> profileOpt = profileRepository.findById(id);
        if(!profileOpt.isPresent() || profileOpt.get().getDeleted()) {
            throw new NotFoundException("Hồ sơ không tồn tại!");
        }

        if(!authorId.equals(profileOpt.get().getAuthorId())) {
            throw new AppException("Có lỗi trong quá trình sử lý!");
        }

        CandidateProfile profile = profileOpt.get();
        profile.setDeleted(false);

        //TODO delete recruitment details

        profileRepository.save(profile);

        ApiResponse<Boolean> res = ApiResponse.<Boolean>builder()
                .message("Xóa hồ sơ thành công!")
                .status(HttpStatus.NO_CONTENT.value())
                .result(true)
                .build();

        return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ApiResponse<ProfileResponse>> get(String id) {
        Optional<CandidateProfile> profileOpt = profileRepository.findById(id);
        if(!profileOpt.isPresent() || profileOpt.get().getDeleted()) {
            throw new NotFoundException("Hồ sơ không tồn tại!");
        }

        ProfileResponse profileResponse = profileMapper.toProfileResponse(profileOpt.get());

        ApiResponse<ProfileResponse> res = ApiResponse.<ProfileResponse>builder()
                .result(profileResponse)
                .status(200)
                .build();

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Map<String, Object>>> get(String search, Pageable pageable) {
        String authorId = authHelper.getCurrentUserId();
        Page<CandidateProfile> pageResult = profileRepository.searchByAuthorIdAndKeyword(authorId, search, pageable);

        Map<String, Object> result = new HashMap<>();
        result.put("profiles", pageResult.getContent().stream().map(item -> profileMapper.toProfileResponse(item)).collect(Collectors.toList()));
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

    public ResponseEntity<ApiResponse<List<ProfileResponse>>> get(ApplicantRequest request) {
        List<CandidateProfile> profiles = profileRepository.findByIdInAndDeletedFalse(request.getIds());
        List<ProfileResponse> results = profiles.stream().map(item -> profileMapper.toProfileResponse(item)).collect(Collectors.toList());
        ApiResponse<List<ProfileResponse>> response = ApiResponse.<List<ProfileResponse>>builder()
                .status(200)
                .result(results)
                .build();
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

}
