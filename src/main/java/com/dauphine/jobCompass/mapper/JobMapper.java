package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.Job.JobCreationRequest;
import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.model.Job;
import com.dauphine.jobCompass.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobMapper {

    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "categoryId", source = "category.id")
    JobDTO toDto(Job job);
}