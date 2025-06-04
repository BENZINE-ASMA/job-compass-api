package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.exceptions.ResourceNotFoundException;
import com.dauphine.jobCompass.model.JobCategory;
import com.dauphine.jobCompass.services.JobCategory.JobCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Job Categories", description = "Management of job categories")
public class JobCategoryController {

    private final JobCategoryService jobCategoryService;

    public JobCategoryController(JobCategoryService jobCategoryService) {
        this.jobCategoryService = jobCategoryService;
    }

    @GetMapping
    @Operation(
            summary = "Get all job categories",
            description = "Retrieve all existing job categories"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<JobCategory>> getAllCategories() {
        List<JobCategory> categories = jobCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get job category by ID",
            description = "Retrieve a specific job category by its unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found and retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<JobCategory> getCategoryById(
            @Parameter(description = "Category unique identifier", required = true)
            @PathVariable UUID id
    ) throws ResourceNotFoundException {
        JobCategory category = jobCategoryService.getJobCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    @Operation(
            summary = "Create new job category",
            description = "Create a new job category"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<JobCategory> createCategory(
            @Parameter(description = "Category data to create", required = true)
            @RequestBody JobCategory category
    ) {
        JobCategory created = jobCategoryService.createCategory(category);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }
}