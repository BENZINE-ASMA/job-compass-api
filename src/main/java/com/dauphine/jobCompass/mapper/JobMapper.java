package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.Job.JobCreationRequest;
import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.model.Job;
import com.dauphine.jobCompass.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class, SkillMapper.class})
public interface JobMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "company", target = "company")
    @Mapping(source = "category", target = "category")
    JobDTO toDto(Job job);
}