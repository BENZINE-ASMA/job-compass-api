package com.dauphine.jobCompass.controllers;


import com.dauphine.jobCompass.dto.Application.ApplicationDTO;
import com.dauphine.jobCompass.dto.Application.ApplicationRequestDTO;
import com.dauphine.jobCompass.dto.User.SimpleUserDTO;
import com.dauphine.jobCompass.model.Application;
import com.dauphine.jobCompass.services.Application.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/applications")
    public ResponseEntity<ApplicationDTO> applyToJob(@RequestBody ApplicationRequestDTO dto) {
        ApplicationDTO saved = applicationService.applyToJob(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/users/{userId}/applications")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsForUser(@PathVariable UUID userId) {
        List<ApplicationDTO> applications = applicationService.getApplicationsByUserId(userId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/jobs/{jobId}/applications")
    public ResponseEntity<List<ApplicationDTO>> getApplicantsByJobId(@PathVariable UUID jobId) {
        List<ApplicationDTO> applicants = applicationService.getApplicantsByJobId(jobId);
        return ResponseEntity.ok(applicants);
    }
}
