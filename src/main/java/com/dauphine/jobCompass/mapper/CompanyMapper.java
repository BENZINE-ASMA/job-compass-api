package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.Company.CompanyDTO;
import com.dauphine.jobCompass.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDTO toDto(Company company);
}