package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.ApplicationDTO;
import com.dauphine.jobCompass.model.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class, UserMapper.class})
public interface ApplicationMapper {
    @Mapping(source = "job", target = "job")
    @Mapping(source = "user", target = "user")
    ApplicationDTO toDto(Application application);
}
