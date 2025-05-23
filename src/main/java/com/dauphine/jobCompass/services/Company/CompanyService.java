package com.dauphine.jobCompass.services.Company;

import com.dauphine.jobCompass.model.Company;

import java.util.List;

public interface CompanyService {
    public List<Company> getAllCompanies();
    public List<String> getAllCompaniesNames();
}
