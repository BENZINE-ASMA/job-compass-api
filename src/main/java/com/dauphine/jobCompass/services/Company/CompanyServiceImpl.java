package com.dauphine.jobCompass.services.Company;

import java.util.List;

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
}
