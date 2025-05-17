package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.CompanyDTO;
import com.dauphine.jobCompass.model.Company;

public interface CompanyMapper {

    CompanyDTO toDto(Company company);
}
