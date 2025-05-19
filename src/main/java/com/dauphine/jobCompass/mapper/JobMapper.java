package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.model.Job;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper {

    JobDTO toDto(Job job);
}
