package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.Application.ApplicationDTO;
import com.dauphine.jobCompass.model.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {JobMapper.class})
public interface ApplicationMapper {

    @Mapping(source = "job", target = "job")
    ApplicationDTO toDto(Application application);

    List<ApplicationDTO> toDtoList(List<Application> applications);
}
