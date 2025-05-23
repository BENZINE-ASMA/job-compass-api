package com.dauphine.jobCompass.services.Category;

import com.dauphine.jobCompass.model.JobCategory;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    public List<JobCategory> getAllCategories();
    public List<String> getAllCategoriesNames();
    public JobCategory getCategoryById(String categoryId);
}
