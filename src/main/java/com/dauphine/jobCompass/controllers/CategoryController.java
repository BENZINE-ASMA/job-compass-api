package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.model.JobCategory;
import com.dauphine.jobCompass.services.Category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @Operation(summary = "Get all categories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved categories")
    })
    @GetMapping("/Categories")
    public ResponseEntity<List<JobCategory>> getAllCategories() {
        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }
    @Operation(summary = "Get all categories name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved categories")
    })
    @GetMapping("/Categories/Name")
    public ResponseEntity<List<String>> getAllCategoriesName() {
        return ResponseEntity.ok(this.categoryService.getAllCategoriesNames());
    }




}
