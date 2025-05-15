package com.kachina.company_service.repository;

import com.kachina.company_service.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    @Query("SELECT c FROM Company c LEFT JOIN FETCH c.fields WHERE c.authorId = :authorId")
    Optional<Company> findByAuthor_id(@Param("authorId") String authorId);

    Page<Company> findByNameContainingIgnoreCase(String search, Pageable pageable);

    @Query("SELECT c.id FROM Company c WHERE :field MEMBER OF c.fields")
    List<String> findCompanyIdsByField(@Param("field") Short field);

    List<Company> findByAuthorIdIn(List<String> authorIds);
}
