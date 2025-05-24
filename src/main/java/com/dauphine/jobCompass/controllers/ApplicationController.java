package com.dauphine.jobCompass.controllers;


import com.dauphine.jobCompass.dto.Application.ApplicationDTO;
import com.dauphine.jobCompass.dto.Application.ApplicationRequestDTO;
import com.dauphine.jobCompass.model.Application;
import com.dauphine.jobCompass.services.Application.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationDTO> applyToJob(@RequestBody ApplicationRequestDTO dto) {
        ApplicationDTO saved = applicationService.applyToJob(dto);
        return ResponseEntity.ok(saved);
    }
}
