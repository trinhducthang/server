package com.kachina.job_service.mapper;

import com.kachina.job_service.dto.request.JobRequest;
import com.kachina.job_service.dto.response.CompanyResponse;
import com.kachina.job_service.dto.response.JobResponse;
import com.kachina.job_service.entity.Job;
import net.htmlparser.jericho.CharacterReference;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class JobMapper {

    public Job toJob(JobRequest request, String authorId) {
        return Job.builder()
                .title(CharacterReference.encode(request.getTitle()))
                .description(CharacterReference.encode(request.getDescription()))
                .benefit(CharacterReference.encode(request.getBenefit()))
                .requirement(CharacterReference.encode(request.getRequirement()))
                .location(CharacterReference.encode(String.join(",", request.getLocation())))
                .locationDetails(CharacterReference.encode(request.getLocation_details()))
                .salary(request.getSalary())
                .deadline(request.getDeadline())
                .exp(request.getExp())
                .formOfWork(request.getForm_of_work())
                .gender(request.getGender())
                .jobField(request.getJob_field())
                .numberOfRecruits(request.getNumber_of_recruits())
                .rank(request.getRank())
                .authorId(authorId)
                .build();
    }

    public Job toJob(Job job, JobRequest request) {
        job.setTitle(CharacterReference.encode(request.getTitle()));
        job.setDescription(CharacterReference.encode(request.getDescription()));
        job.setBenefit(CharacterReference.encode(request.getBenefit()));
        job.setRequirement(CharacterReference.encode(request.getRequirement()));
        job.setLocation(CharacterReference.encode(String.join(",", request.getLocation())));
        job.setLocationDetails(CharacterReference.encode(request.getLocation_details()));
        job.setSalary(request.getSalary());
        job.setDeadline(request.getDeadline());
        job.setExp(request.getExp());
        job.setFormOfWork(request.getForm_of_work());
        job.setGender(request.getGender());
        job.setJobField(request.getJob_field());
        job.setNumberOfRecruits(request.getNumber_of_recruits());
        job.setRank(request.getRank());
        return job;
    }

    public JobResponse toJobResponse(Job job, CompanyResponse company) {
        return JobResponse.builder()
                .id(job.getId())
                .title(CharacterReference.decode(job.getTitle()))
                .description(CharacterReference.decode(job.getDescription()))
                .benefit(CharacterReference.decode(job.getBenefit()))
                .requirement(CharacterReference.decode(job.getRequirement()))
                .location(Arrays.stream(CharacterReference.decode(job.getLocation()).split(",")).toList())
                .location_details(CharacterReference.decode(job.getLocationDetails()))
                .salary(job.getSalary())
                .deadline(job.getDeadline())
                .exp(job.getExp())
                .form_of_work(job.getFormOfWork())
                .gender(job.getGender())
                .job_field(job.getJobField())
                .number_of_recruits(job.getNumberOfRecruits())
                .rank(job.getRank())
                .created_at(job.getCreatedAt())
                .updated_at(job.getUpdatedAt())
                .company(company)
                .enable(job.getEnabled())
                .build();
    }

}
