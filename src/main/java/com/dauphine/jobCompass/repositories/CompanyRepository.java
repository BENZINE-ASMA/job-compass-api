package com.dauphine.jobCompass.repositories;

import com.dauphine.jobCompass.model.Company;
import com.dauphine.jobCompass.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
