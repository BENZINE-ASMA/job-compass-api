package com.dauphine.jobCompass.repositories;

import com.dauphine.jobCompass.model.Company;
import com.dauphine.jobCompass.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    @Query("SELECT c.name FROM Company c")
    List<String> findAllCompanyNames();
}
