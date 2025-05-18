package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.Application.ApplicationDTO;
import com.dauphine.jobCompass.model.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class, UserMapper.class})
public interface ApplicationMapper {
    @Mapping(source = "job.id", target = "jobId")
    @Mapping(source = "user.id", target = "userId")
    ApplicationDTO toDto(Application application);

}
