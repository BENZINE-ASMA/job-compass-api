package com.dauphine.jobCompass.controllers;
import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.dto.User.SimpleUserDTO;
import com.dauphine.jobCompass.services.Job.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")

public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
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
