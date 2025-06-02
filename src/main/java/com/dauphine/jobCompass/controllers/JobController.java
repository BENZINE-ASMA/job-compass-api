package com.dauphine.jobCompass.controllers;
import com.dauphine.jobCompass.dto.Job.JobCreationRequest;
import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.dto.JobFilters.JobFilters;
import com.dauphine.jobCompass.dto.User.SimpleUserDTO;
import com.dauphine.jobCompass.exceptions.ResourceNotFoundException;
import com.dauphine.jobCompass.model.Job;
import com.dauphine.jobCompass.services.Application.ApplicationService;
import com.dauphine.jobCompass.services.Job.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")

public class JobController {
    private final JobService jobService;
    private final ApplicationService applicationService;

    public JobController(JobService jobService,ApplicationService applicationService) {
        this.jobService = jobService;
        this.applicationService = applicationService;
    }

    @Operation(summary = "Get all jobs by owner ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved jobs"),
            @ApiResponse(responseCode = "400", description = "Invalid UUID format")
    })
    @GetMapping("/Jobs/owner/{ownerId}")
    public ResponseEntity<List<JobDTO>> getJobsByOwnerId(@PathVariable String ownerId) {
        try {
            UUID uuid = UUID.fromString(ownerId);
            List<JobDTO> jobs = jobService.getAllOwnersJobs(uuid);
            return ResponseEntity.ok(jobs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList()); // ou un message d'erreur
        }
    }

    @Operation(summary = "Get all users with basic information",
            description = "Retrieves all simple user's information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users")
    })
    @GetMapping("/Jobs")
    public ResponseEntity<List<JobDTO>> getAllSimpleJobs() {
        return ResponseEntity.ok(this.jobService.getAllJobs());
    }

    @GetMapping("/Jobs/filter")
    public ResponseEntity<List<JobDTO>> filterJobs(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String contractType,
            @RequestParam(required = false) String location) {

        List<JobDTO> jobs = jobService.getFilteredJobs(
                new JobFilters(search, category, contractType, location, null)
        );
        return ResponseEntity.ok(jobs);
    }
    @GetMapping("/Jobs/filter/my-jobs")
    public ResponseEntity<List<JobDTO>> filterJobsByOwner(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String contractType,
            @RequestParam(required = false) String location,
            @RequestParam(required = true) UUID  ownerId) {

        List<JobDTO> jobs = jobService.getFilteredJobsByOwnerId(
                new JobFilters(search, category, contractType, location, ownerId)
        );
        return ResponseEntity.ok(jobs);
    }
    @Operation(summary = "Create new job")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users")
    })
    @PostMapping("/Jobs")
    public ResponseEntity<JobDTO> createJob(@RequestBody JobCreationRequest jobRequest) {
        JobDTO createdJob = jobService.createJob(jobRequest);
        return ResponseEntity.ok(createdJob);
    }

    @GetMapping("Jobs/{id}")
    public ResponseEntity<JobDTO> getJobById(
            @Parameter(description = "UUID of the job to be retrieved", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id) {

        JobDTO job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @GetMapping("Jobs/locations")
    public List<String> getAllLocations() {
        return jobService.getAllLocations();
    }

    @DeleteMapping("/Job/delete/{id}")
    @Operation(summary = "Delete a job by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Job deleted"),
            @ApiResponse(responseCode = "404", description = "Job not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> deleteJob(@PathVariable UUID id) {
        try {
            jobService.deleteJob(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Job not found with id: " + id); // Simple String
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete job: " + e.getMessage());
        }
    }


}
 /*   // GET /api/v1/jobs (Filtres optionnels)
    @GetMapping
    public List<Job> searchJobs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) Double minSalary) { ... }

    // GET /api/v1/jobs/{id}
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Long id) { ... }

    // POST /api/v1/jobs
    @PostMapping
    public Job createJob(@RequestBody JobCreationRequest request) { ... }

    // DELETE /api/v1/jobs/{id}
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) { ... }

}
 */
