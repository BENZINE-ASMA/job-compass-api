package com.dauphine.jobCompass.services.JobCategory;

import com.dauphine.jobCompass.model.JobCategory;

import java.util.List;
import java.util.UUID;

public interface JobCategoryService {
    JobCategory getJobCategoryById(UUID jobCategoryId);
    List<JobCategory> getAllCategories();
    JobCategory createCategory(JobCategory category);

}
