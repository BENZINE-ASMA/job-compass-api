package com.dauphine.jobCompass.services.Category;

import com.dauphine.jobCompass.model.JobCategory;

import java.util.List;

public interface CategoryService {
    public List<JobCategory> getAllCategories();
    public List<String> getAllCategoriesNames();
}
