package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.Skill.AddSkillsToJobRequest;
import com.dauphine.jobCompass.dto.Skill.SkillDTO;
import com.dauphine.jobCompass.mapper.SkillMapper;
import com.dauphine.jobCompass.services.skill.JobSkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobSkillController {

    private final JobSkillService jobSkillService;
    private final SkillMapper skillMapper;

    public JobSkillController(JobSkillService jobSkillService, SkillMapper skillMapper) {
        this.jobSkillService = jobSkillService;
        this.skillMapper = skillMapper;
    }

    @PostMapping("/{jobId}/skills")
    public ResponseEntity<?> addSkillsToJob(
            @PathVariable UUID jobId,
            @RequestBody AddSkillsToJobRequest request
    ) {
        try {
            jobSkillService.addSkillsToJob(jobId, request.getSkillIds(), request.getSkillNames());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Skills added to job successfully.");
            response.put("success", true);
            response.put("skillsAdded", request.getSkillIds().size() + request.getSkillNames().size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("success", false);

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


    @DeleteMapping("/{jobId}/skills/{skillId}")
    public ResponseEntity<?> removeSkillFromJob(
            @PathVariable UUID jobId,
            @PathVariable UUID skillId
    ) {
        jobSkillService.removeSkillFromJob(jobId, skillId);
        return ResponseEntity.ok("Skill removed from job.");
    }

    @GetMapping("/{jobId}/skills")
    public ResponseEntity<Set<SkillDTO>> getSkillsForJob(@PathVariable UUID jobId) {
        Set<SkillDTO> dtos = skillMapper.toDTOSet(jobSkillService.getSkillsForJob(jobId));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{jobId}/skills/{skillId}/has")
    public ResponseEntity<Boolean> jobHasSkill(
            @PathVariable UUID jobId,
            @PathVariable UUID skillId
    ) {
        boolean result = jobSkillService.jobHasSkill(jobId, skillId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{jobId}/skills/has-name")
    public ResponseEntity<Boolean> jobHasSkillByName(
            @PathVariable UUID jobId,
            @RequestParam String name
    ) {
        boolean result = jobSkillService.jobHasSkillByName(jobId, name);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{jobId}/skills/count")
    public ResponseEntity<Long> countSkillsForJob(@PathVariable UUID jobId) {
        long count = jobSkillService.countSkillsForJob(jobId);
        return ResponseEntity.ok(count);
    }
    @DeleteMapping("/{jobId}/skills")
    public ResponseEntity<?> removeAllSkillsFromJob(@PathVariable UUID jobId) {
        jobSkillService.removeAllSkillsFromJob(jobId);
        return ResponseEntity.ok("All skills removed from job.");
    }

}
