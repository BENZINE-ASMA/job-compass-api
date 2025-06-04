package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.Skill.AddSkillsToJobRequest;
import com.dauphine.jobCompass.dto.Skill.SkillDTO;
import com.dauphine.jobCompass.exceptions.JobNotFoundException;
import com.dauphine.jobCompass.exceptions.SkillNotFoundException;
import com.dauphine.jobCompass.mapper.SkillMapper;
import com.dauphine.jobCompass.services.skill.JobSkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/jobs")
@Tag(name = "Job Skills", description = "Management of skills associated with jobs")
public class JobSkillController {

    private final JobSkillService jobSkillService;
    private final SkillMapper skillMapper;

    public JobSkillController(JobSkillService jobSkillService, SkillMapper skillMapper) {
        this.jobSkillService = jobSkillService;
        this.skillMapper = skillMapper;
    }

    @PostMapping("/{jobId}/skills")
    @Operation(
            summary = "Add skills to a job",
            description = "Add multiple skills to a specific job using skill IDs and/or skill names"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skills successfully added to job"),
            @ApiResponse(responseCode = "400", description = "Invalid request - bad job ID or skill data"),
            @ApiResponse(responseCode = "404", description = "Job not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> addSkillsToJob(
            @Parameter(description = "Job unique identifier", required = true)
            @PathVariable UUID jobId,
            @Parameter(description = "Request containing skill IDs and names to add", required = true)
            @RequestBody AddSkillsToJobRequest request
    ) throws JobNotFoundException, SkillNotFoundException {
        jobSkillService.addSkillsToJob(jobId, request.getSkillIds(), request.getSkillNames());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Skills added to job successfully.");
        response.put("success", true);
        response.put("skillsAdded", request.getSkillIds().size() + request.getSkillNames().size());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{jobId}/skills/{skillId}")
    @Operation(
            summary = "Remove skill from job",
            description = "Remove a specific skill from a job"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skill successfully removed from job"),
            @ApiResponse(responseCode = "404", description = "Job or skill not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Map<String, String>> removeSkillFromJob(
            @Parameter(description = "Job unique identifier", required = true)
            @PathVariable UUID jobId,
            @Parameter(description = "Skill unique identifier", required = true)
            @PathVariable UUID skillId
    ) throws JobNotFoundException, SkillNotFoundException {
        jobSkillService.removeSkillFromJob(jobId, skillId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Skill removed from job successfully");
        response.put("success", "true");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{jobId}/skills")
    @Operation(
            summary = "Get all skills for a job",
            description = "Retrieve all skills associated with a specific job"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skills retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Job not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Set<SkillDTO>> getSkillsForJob(
            @Parameter(description = "Job unique identifier", required = true)
            @PathVariable UUID jobId
    ) throws JobNotFoundException {
        Set<SkillDTO> dtos = skillMapper.toDTOSet(jobSkillService.getSkillsForJob(jobId));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{jobId}/skills/{skillId}/has")
    @Operation(
            summary = "Check if job has specific skill",
            description = "Verify if a job has a specific skill associated with it"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check completed successfully"),
            @ApiResponse(responseCode = "404", description = "Job or skill not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Boolean> jobHasSkill(
            @Parameter(description = "Job unique identifier", required = true)
            @PathVariable UUID jobId,
            @Parameter(description = "Skill unique identifier", required = true)
            @PathVariable UUID skillId
    ) throws JobNotFoundException, SkillNotFoundException {
        boolean result = jobSkillService.jobHasSkill(jobId, skillId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{jobId}/skills/has-name")
    @Operation(
            summary = "Check if job has skill by name",
            description = "Verify if a job has a skill with the specified name"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check completed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid skill name provided"),
            @ApiResponse(responseCode = "404", description = "Job not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Boolean> jobHasSkillByName(
            @Parameter(description = "Job unique identifier", required = true)
            @PathVariable UUID jobId,
            @Parameter(description = "Skill name to search for", required = true)
            @RequestParam String name
    ) throws JobNotFoundException {
        boolean result = jobSkillService.jobHasSkillByName(jobId, name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{jobId}/skills/count")
    @Operation(
            summary = "Count skills for a job",
            description = "Get the total number of skills associated with a specific job"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Job not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Long> countSkillsForJob(
            @Parameter(description = "Job unique identifier", required = true)
            @PathVariable UUID jobId
    ) throws JobNotFoundException {
        long count = jobSkillService.countSkillsForJob(jobId);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/{jobId}/skills")
    @Operation(
            summary = "Remove all skills from job",
            description = "Remove all skills associated with a specific job"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All skills successfully removed from job"),
            @ApiResponse(responseCode = "404", description = "Job not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Map<String, String>> removeAllSkillsFromJob(
            @Parameter(description = "Job unique identifier", required = true)
            @PathVariable UUID jobId
    ) throws JobNotFoundException {
        jobSkillService.removeAllSkillsFromJob(jobId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "All skills removed from job successfully");
        response.put("success", "true");

        return ResponseEntity.ok(response);
    }
}