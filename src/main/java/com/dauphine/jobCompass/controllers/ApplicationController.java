package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.Application.ApplicationDTO;
import com.dauphine.jobCompass.dto.Application.ApplicationRequestDTO;
import com.dauphine.jobCompass.dto.Application.UpdateApplicationStatusDTO;
import com.dauphine.jobCompass.model.enums.ApplicationStatus;
import com.dauphine.jobCompass.services.Application.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Applications", description = "API pour la gestion des candidatures")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/applications")
    @Operation(
            summary = "Postuler à une offre d'emploi",
            description = "Créer une nouvelle candidature pour un utilisateur et une offre d'emploi"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Candidature créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données de candidature invalides"),
            @ApiResponse(responseCode = "404", description = "Utilisateur ou offre d'emploi non trouvé"),
            @ApiResponse(responseCode = "409", description = "L'utilisateur a déjà postulé pour cette offre"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<ApplicationDTO> applyToJob(@RequestBody ApplicationRequestDTO dto) {
        ApplicationDTO saved = applicationService.applyToJob(dto);
        return ResponseEntity
                .created(URI.create("/api/v1/applications/" + saved.getId()))
                .body(saved);
    }

    @GetMapping("/users/{userId}/applications")
    @Operation(
            summary = "Récupérer les candidatures d'un utilisateur",
            description = "Obtenir toutes les candidatures soumises par un utilisateur spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des candidatures récupérée avec succès"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<ApplicationDTO>> getApplicationsForUser(
            @Parameter(description = "ID de l'utilisateur", required = true)
            @PathVariable UUID userId) {
        List<ApplicationDTO> applications = applicationService.getApplicationsByUserId(userId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/jobs/{jobId}/applications")
    @Operation(
            summary = "Récupérer les candidatures pour une offre d'emploi",
            description = "Obtenir toutes les candidatures pour une offre d'emploi spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des candidatures récupérée avec succès"),
            @ApiResponse(responseCode = "404", description = "Offre d'emploi non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<ApplicationDTO>> getApplicantsByJobId(
            @Parameter(description = "ID de l'offre d'emploi", required = true)
            @PathVariable UUID jobId) {
        List<ApplicationDTO> applicants = applicationService.getApplicantsByJobId(jobId);
        return ResponseEntity.ok(applicants);
    }

    @PutMapping("/applications/{applicationId}/status")
    @Operation(
            summary = "Mettre à jour le statut d'une candidature",
            description = "Modifier le statut d'une candidature existante"
    )
    public ResponseEntity<ApplicationDTO> updateApplicationStatus(
            @PathVariable UUID applicationId,
            @Valid @RequestBody UpdateApplicationStatusDTO updateDTO) {

        ApplicationDTO updated = applicationService.updateApplicationStatus(
                applicationId,
                updateDTO.getStatus()
        );
        return ResponseEntity.ok(updated);
    }
}