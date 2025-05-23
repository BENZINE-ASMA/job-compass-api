package com.dauphine.jobCompass.services.Category;

import com.dauphine.jobCompass.model.JobCategory;
import com.dauphine.jobCompass.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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



}
