package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.Company.CompanyDTO;
import com.dauphine.jobCompass.model.Company;
import org.mapstruct.Mapping;

public interface CompanyMapper {
    @Mapping(target = "company",  source = "company.id")
    CompanyDTO toDto(Company company);
}