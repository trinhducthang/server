package com.kachina.profile_service.mapper;

import com.kachina.profile_service.dto.request.ProfileRequest;
import com.kachina.profile_service.dto.response.ProfileResponse;
import com.kachina.profile_service.entity.CandidateProfile;
import net.htmlparser.jericho.CharacterReference;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public CandidateProfile toCandidateProfile(ProfileRequest request, String authorId) {
         return CandidateProfile.builder()
                 .name(encode(request.getName()))
                 .fullname(encode(request.getFullname()))
                 .email(request.getEmail())
                 .phone(request.getPhone())
                 .address(encode(request.getAddress()))
                 .dateOfBirth(request.getDate_of_birth())
                 .position(encode(request.getPosition()))
                 .career(encode(request.getCareer()))
                 .introduction(encode(request.getIntroduction()))
                 .skills(encode(request.getSkills()))
                 .exp(encode(request.getExp()))
                 .education(encode(request.getEducation()))
                 .achievement(encode(request.getAchievement()))
                 .language(encode(request.getLanguage()))
                 .hobbies(encode(request.getHobbies()))
                 .other(encode(request.getOther()))
                 .tags(encode(request.getTags()))
                 .authorId(authorId)
                 .build();
    }

    public ProfileResponse toProfileResponse(CandidateProfile profile) {
        return ProfileResponse.builder()
                .id(profile.getId())
                .name(decode(profile.getName()))
                .fullname(decode(profile.getFullname()))
                .email(profile.getEmail())
                .phone(profile.getPhone())
                .address(decode(profile.getAddress()))
                .date_of_birth(profile.getDateOfBirth())
                .position(decode(profile.getPosition()))
                .career(decode(profile.getCareer()))
                .introduction(decode(profile.getIntroduction()))
                .skills(decode(profile.getSkills()))
                .exp(decode(profile.getExp()))
                .education(decode(profile.getEducation()))
                .achievement(decode(profile.getAchievement()))
                .language(decode(profile.getLanguage()))
                .hobbies(decode(profile.getHobbies()))
                .other(decode(profile.getOther()))
                .tags(decode(profile.getTags()))
                .created_at(profile.getCreatedAt())
                .updated_at(profile.getUpdatedAt())
                .build();
    }

    private String decode(String encoded) {
        return CharacterReference.decode(encoded);
    }

    private String encode(String raw) {
        return CharacterReference.encode(raw);
    }

}
