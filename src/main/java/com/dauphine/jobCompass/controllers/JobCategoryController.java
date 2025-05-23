package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.model.JobCategory;
import com.dauphine.jobCompass.services.JobCategory.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class JobCategoryController {

    private final JobCategoryService jobCategoryService;

    @Autowired
    public JobCategoryController(JobCategoryService jobCategoryService) {
        this.jobCategoryService = jobCategoryService;
    }

    // 🔹 GET /api/v1/categories
    @GetMapping
    public ResponseEntity<List<JobCategory>> getAllCategories() {
        List<JobCategory> categories = jobCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobCategory> getCategoryById(@PathVariable UUID id) {
        JobCategory category = jobCategoryService.getJobCategoryById(id);
        return ResponseEntity.ok(category);
    }


    // 🔹 POST /api/v1/categories
    @PostMapping
    public ResponseEntity<JobCategory> createCategory(@RequestBody JobCategory category) {
        JobCategory created = jobCategoryService.createCategory(category);
        return ResponseEntity.status(201).body(created);
    }
}
