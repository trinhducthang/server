package com.kachina.profile_service.dto.projection;

import java.util.Date;

public interface CandidateProfileProjection {

    String getId();
    String getName();
    String getTags();
    Date getUpdatedAt();

}
