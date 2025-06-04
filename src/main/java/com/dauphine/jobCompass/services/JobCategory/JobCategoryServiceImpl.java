package com.dauphine.jobCompass.services.JobCategory;

import com.dauphine.jobCompass.model.JobCategory;
import com.dauphine.jobCompass.repositories.JobCategoryRepository;
import com.dauphine.jobCompass.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JobCategoryServiceImpl implements JobCategoryService {

    private final JobCategoryRepository jobCategoryRepository;

    public JobCategoryServiceImpl(JobCategoryRepository jobCategoryRepository) {
        this.jobCategoryRepository = jobCategoryRepository;
    }

    @Override
    public JobCategory getJobCategoryById(UUID jobCategoryId) throws ResourceNotFoundException {
        if (jobCategoryId == null) {
            throw new IllegalArgumentException("Job category ID cannot be null");
        }

        return jobCategoryRepository.findById(jobCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job Category not found with id: " + jobCategoryId));
    }

    @Override
    public List<JobCategory> getAllCategories() {
        return jobCategoryRepository.findAll();
    }

    @Override
    public JobCategory createCategory(JobCategory category) throws IllegalArgumentException {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name is required and cannot be empty");
        }


        category.setName(category.getName().trim());


        if (category.getId() == null) {
            category.setId(UUID.randomUUID());
        }

        return jobCategoryRepository.save(category);
    }
}