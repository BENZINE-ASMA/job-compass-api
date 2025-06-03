package com.dauphine.jobCompass.repositories;

import com.dauphine.jobCompass.model.Company;
import com.dauphine.jobCompass.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    @Query("SELECT c.name FROM Company c")
    List<String> findAllCompanyNames();

    @Query(value = """
                SELECT * FROM companies c 
                WHERE (:search IS NULL OR c.name ILIKE CONCAT('%', :search, '%'))
                AND (:location IS NULL OR c.location ILIKE CONCAT('%', :location, '%'))
            """, nativeQuery = true)
    List<Company> filterCompanies(@Param("search") String search, @Param("location") String location);

}
