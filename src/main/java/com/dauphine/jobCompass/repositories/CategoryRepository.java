package com.dauphine.jobCompass.repositories;

import com.dauphine.jobCompass.model.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<JobCategory, UUID> {
    @Query("SELECT jc.name FROM JobCategory jc")
    List<String> findAllCategoryNames();
}
