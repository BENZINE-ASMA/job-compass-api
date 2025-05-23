package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.model.Job;
import com.dauphine.jobCompass.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobMapper {
    @Mapping(target = "owner", ignore = true)
    JobDTO toDto(Job job);

}
