package com.dauphine.jobCompass.services.Company;

import com.dauphine.jobCompass.dto.Company.CompanyCreationRequest;
import com.dauphine.jobCompass.dto.Company.CompanyDTO;
import com.dauphine.jobCompass.model.Company;

import java.util.List;
import java.util.UUID;

public interface CompanyService {
    List<Company> getAllCompanies();
    List<String> getAllCompaniesNames();
    Company getCompanyById(UUID companyId);
    Company createCompany(CompanyCreationRequest request);
    List<CompanyDTO> filterCompanies(String search, String location);

}
