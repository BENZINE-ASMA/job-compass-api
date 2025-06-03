package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.Skill.SkillDTO;
import com.dauphine.jobCompass.mapper.SkillMapper;
import com.dauphine.jobCompass.services.skill.JobSkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

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
            @RequestBody List<UUID> skillIds
    ) {
        jobSkillService.addSkillsToJob(jobId, skillIds);
        return ResponseEntity.ok("Skills added to job.");
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

    @PostMapping(path = "/{jobId}/skills/by-name",consumes = "text/plain")
    public ResponseEntity<SkillDTO> addSkillByName(
            @PathVariable UUID jobId,
            @RequestBody String skillName
    ) {
        SkillDTO dto = jobSkillService.addSkillToJobByName(jobId, skillName);
        return ResponseEntity.ok(dto);
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
