package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.ApplicationDTO;
import com.dauphine.jobCompass.model.Application;

//@Mapper(componentModel="spring", uses={CompanyMa})
public interface ApplicationMapper {
    ApplicationDTO toDto(Application application);
}
