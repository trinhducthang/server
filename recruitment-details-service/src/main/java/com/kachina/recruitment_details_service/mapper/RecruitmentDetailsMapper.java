package com.kachina.recruitment_details_service.mapper;

import com.kachina.recruitment_details_service.dto.response.AppliedJobResponse;
import com.kachina.recruitment_details_service.dto.response.RecruitmentDetailsResponse;
import com.kachina.recruitment_details_service.entity.RecruitmentDetails;
import net.htmlparser.jericho.CharacterReference;
import org.springframework.stereotype.Component;

@Component
public class RecruitmentDetailsMapper {

    public RecruitmentDetailsResponse toRecruitmentDetailsResponse(RecruitmentDetails rd) {
        return RecruitmentDetailsResponse.builder()
                .id(rd.getId())
                .feedback(CharacterReference.decode(rd.getFeedback()))
                .status(rd.getStatus())
                .viewed(rd.isViewed())
                .application_date(rd.getApplicationDate())
                .build();
    }

}
