package com.kachina.job_service.specification;

import com.kachina.job_service.dto.request.JobFilterRequest;
import com.kachina.job_service.entity.Job;
import jakarta.persistence.criteria.Predicate;
import net.htmlparser.jericho.CharacterReference;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JobSpecification {

    public Specification<Job> getFilteredJobs(JobFilterRequest filter, List<String> authorIds) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getSearch() != null && !filter.getSearch().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + CharacterReference.encode(filter.getSearch()).toLowerCase() + "%"));
            }

            if(filter.getLocation() != null && !filter.getLocation().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("location")), "%" + CharacterReference.encode(filter.getLocation()).toLowerCase() + "%"));
            }

            if (filter.getCategory() != null && filter.getCategory() > 0) {
                predicates.add(cb.equal(root.get("category"), filter.getCategory()));
            }

            if (filter.getJob_field() != null && filter.getJob_field() > 0) {
                predicates.add(cb.equal(root.get("jobField"), filter.getJob_field()));
            }

            if (filter.getCompany_field() != null && filter.getCompany_field() > 0 && !authorIds.isEmpty()) {
                predicates.add(root.get("authorId").in(authorIds));
            }

            if (filter.getSalary() != null && filter.getSalary() > 0) {
                predicates.add(cb.equal(root.get("salary"), filter.getSalary()));
            }

            if (filter.getExp() != null && filter.getExp() > 0) {
                predicates.add(cb.equal(root.get("exp"), filter.getExp()));
            }

            if (filter.getRank() != null && filter.getRank() > 0) {
                predicates.add(cb.equal(root.get("rank"), filter.getRank()));
            }

            if (filter.getForm_of_work() != null && filter.getForm_of_work() > 0) {
                predicates.add(cb.equal(root.get("formOfWork"), filter.getForm_of_work()));
            }

            predicates.add(cb.isTrue(root.get("enabled")));
            predicates.add(cb.isFalse(root.get("deleted")));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
