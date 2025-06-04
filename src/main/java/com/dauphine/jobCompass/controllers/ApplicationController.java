package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.Application.ApplicationDTO;
import com.dauphine.jobCompass.dto.Application.ApplicationRequestDTO;
import com.dauphine.jobCompass.dto.Notification.NotificationDto;
import com.dauphine.jobCompass.exceptions.NotFoundException;
import com.dauphine.jobCompass.model.Application;
import com.dauphine.jobCompass.model.enums.ApplicationStatus;
import com.dauphine.jobCompass.services.Application.ApplicationService;
import com.dauphine.jobCompass.services.Notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final NotificationService notificationService;

    public ApplicationController(ApplicationService applicationService , NotificationService notificationService) {
        this.applicationService = applicationService;
        this.notificationService = notificationService;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statut mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Statut invalide"),
            @ApiResponse(responseCode = "404", description = "Candidature non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<Void> updateApplicationStatus(
            @Parameter(description = "ID de la candidature", required = true)
            @PathVariable UUID applicationId,
            @Parameter(description = "Nouveau statut de la candidature", required = true)
            @RequestParam ApplicationStatus status) {
        applicationService.updateApplicationStatus(applicationId, status);
        if (status == ApplicationStatus.ACCEPTED || status == ApplicationStatus.REJECTED) {
            Application application = applicationService.findById(applicationId)
                    .orElseThrow(() -> new NotFoundException("Application not found"));

            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setCandidateId(application.getUser().getId());
            notificationDto.setRecruiterId(application.getJob().getOwner().getId());
            notificationDto.setApplicationId(applicationId);

            String message = status == ApplicationStatus.ACCEPTED
                    ? "Félicitations ! Votre candidature a été acceptée"
                    : "Votre candidature n'a malheureusement pas été retenue";

            notificationDto.setMessage(message);
            notificationService.createNotification(notificationDto);
        }
        return ResponseEntity.ok().build();
    }
}