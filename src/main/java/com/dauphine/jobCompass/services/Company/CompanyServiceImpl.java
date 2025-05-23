package com.dauphine.jobCompass.services.Company;

import java.util.List;
import java.util.UUID;

import com.dauphine.jobCompass.exceptions.ResourceNotFoundException;
import com.dauphine.jobCompass.model.Company;
import com.dauphine.jobCompass.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService{
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl (CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies;
    }

    @Override
    public List<String> getAllCompaniesNames() {
        List<String> companiesName = companyRepository.findAllCompanyNames();
        return companiesName;
    }

    public Company getCompanyById(UUID companyId) {
        UUID uuid = companyId;
        return companyRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Company not found with id: " + companyId));
    }
}
