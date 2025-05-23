package com.dauphine.jobCompass.repositories;


import com.dauphine.jobCompass.model.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobCategoryRepository extends JpaRepository<JobCategory, UUID> {
}
