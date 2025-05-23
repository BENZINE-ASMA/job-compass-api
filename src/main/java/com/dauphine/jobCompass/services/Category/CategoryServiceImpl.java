package com.dauphine.jobCompass.services.Category;

import com.dauphine.jobCompass.exceptions.ResourceNotFoundException;
import com.dauphine.jobCompass.model.JobCategory;
import com.dauphine.jobCompass.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<JobCategory> getAllCategories(){
        List<JobCategory> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public List<String> getAllCategoriesNames(){
        List<String> categoriesName = categoryRepository.findAllCategoryNames();
        return categoriesName;
    }

    public JobCategory getCategoryById(String categoryId) {
        if (categoryId == null) {
            System.out.println("Category id is null");
        }
        UUID uuid = UUID.fromString(categoryId);
        return categoryRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job Category not found with id: " + categoryId));
    }



}
