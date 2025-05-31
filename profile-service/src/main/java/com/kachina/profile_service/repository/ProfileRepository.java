package com.kachina.profile_service.repository;

import com.kachina.profile_service.dto.projection.CandidateProfileProjection;
import com.kachina.profile_service.entity.CandidateProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<CandidateProfile, String> {

    @Query("SELECT p FROM CandidateProfile p WHERE p.authorId = :authorId AND p.deleted = false AND " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<CandidateProfile> searchByAuthorIdAndKeyword(@Param("authorId") String authorId,
                                                      @Param("keyword") String keyword,
                                                      Pageable pageable);

    List<CandidateProfileProjection> findByAuthorIdOrderByUpdatedAtDesc(String authorId);

    List<CandidateProfileProjection> findByIdInAndDeletedFalse(List<String> ids);

}
