package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.Job.JobCreationRequest;
import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.dto.JobFilters.JobFilters;
import com.dauphine.jobCompass.services.Application.ApplicationService;
import com.dauphine.jobCompass.services.Job.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Jobs", description = "API pour la gestion des offres d'emploi")
public class JobController {

    private final JobService jobService;
    private final ApplicationService applicationService;

    public JobController(JobService jobService, ApplicationService applicationService) {
        this.jobService = jobService;
        this.applicationService = applicationService;
    }

    @GetMapping("/jobs")
    @Operation(
            summary = "Récupérer toutes les offres d'emploi",
            description = "Obtenir la liste complète de toutes les offres d'emploi disponibles"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des offres d'emploi récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/jobs/{id}")
    @Operation(
            summary = "Récupérer une offre d'emploi par ID",
            description = "Obtenir les détails d'une offre d'emploi spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offre d'emploi trouvée"),
            @ApiResponse(responseCode = "404", description = "Offre d'emploi non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<JobDTO> getJobById(
            @Parameter(description = "ID de l'offre d'emploi", required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id) {
        JobDTO job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @GetMapping("/jobs/owner/{ownerId}")
    @Operation(
            summary = "Récupérer les offres d'emploi par propriétaire",
            description = "Obtenir toutes les offres d'emploi créées par un propriétaire spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offres d'emploi récupérées avec succès"),
            @ApiResponse(responseCode = "400", description = "Format UUID invalide"),
            @ApiResponse(responseCode = "404", description = "Propriétaire non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<JobDTO>> getJobsByOwnerId(
            @Parameter(description = "ID du propriétaire", required = true)
            @PathVariable UUID ownerId) {
        List<JobDTO> jobs = jobService.getAllOwnersJobs(ownerId);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/jobs/filter")
    @Operation(
            summary = "Filtrer les offres d'emploi",
            description = "Rechercher des offres d'emploi selon différents critères"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Résultats de recherche récupérés avec succès"),
            @ApiResponse(responseCode = "400", description = "Paramètres de recherche invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<JobDTO>> filterJobs(
            @Parameter(description = "Terme de recherche")
            @RequestParam(required = false) String search,
            @Parameter(description = "Catégorie d'emploi")
            @RequestParam(required = false) String category,
            @Parameter(description = "Type de contrat")
            @RequestParam(required = false) String contractType,
            @Parameter(description = "Localisation")
            @RequestParam(required = false) String location) {

        JobFilters filters = new JobFilters(search, category, contractType, location, null);
        List<JobDTO> jobs = jobService.getFilteredJobs(filters);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/jobs/filter/my-jobs")
    @Operation(
            summary = "Filtrer mes offres d'emploi",
            description = "Rechercher parmi les offres d'emploi d'un propriétaire spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Résultats de recherche récupérés avec succès"),
            @ApiResponse(responseCode = "400", description = "Paramètres de recherche invalides"),
            @ApiResponse(responseCode = "404", description = "Propriétaire non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<JobDTO>> filterJobsByOwner(
            @Parameter(description = "Terme de recherche")
            @RequestParam(required = false) String search,
            @Parameter(description = "Catégorie d'emploi")
            @RequestParam(required = false) String category,
            @Parameter(description = "Type de contrat")
            @RequestParam(required = false) String contractType,
            @Parameter(description = "Localisation")
            @RequestParam(required = false) String location,
            @Parameter(description = "ID du propriétaire", required = true)
            @RequestParam UUID ownerId) {

        JobFilters filters = new JobFilters(search, category, contractType, location, ownerId);
        List<JobDTO> jobs = jobService.getFilteredJobsByOwnerId(filters);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/jobs/locations")
    @Operation(
            summary = "Récupérer toutes les localisations",
            description = "Obtenir la liste de toutes les localisations disponibles pour les offres d'emploi"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des localisations récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<String>> getAllLocations() {
        List<String> locations = jobService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @PostMapping("/jobs")
    @Operation(
            summary = "Créer une nouvelle offre d'emploi",
            description = "Ajouter une nouvelle offre d'emploi dans le système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Offre d'emploi créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données d'offre d'emploi invalides"),
            @ApiResponse(responseCode = "404", description = "Propriétaire ou entreprise non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<JobDTO> createJob(
            @Valid @RequestBody JobCreationRequest jobRequest) {
        JobDTO createdJob = jobService.createJob(jobRequest);
        return ResponseEntity
                .created(URI.create("/api/v1/jobs/" + createdJob.getId()))
                .body(createdJob);
    }

    @PutMapping("/jobs/{id}")
    @Operation(
            summary = "Mettre à jour une offre d'emploi",
            description = "Modifier une offre d'emploi existante"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offre d'emploi mise à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Offre d'emploi non trouvée"),
            @ApiResponse(responseCode = "403", description = "Non autorisé à modifier cette offre"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<JobDTO> updateJob(
            @Parameter(description = "ID de l'offre d'emploi", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody JobCreationRequest request) {
        JobDTO updatedJob = jobService.updateJob(id, request);
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/jobs/{id}")
    @Operation(
            summary = "Supprimer une offre d'emploi",
            description = "Supprimer définitivement une offre d'emploi"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Offre d'emploi supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Offre d'emploi non trouvée"),
            @ApiResponse(responseCode = "403", description = "Non autorisé à supprimer cette offre"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<Void> deleteJob(
            @Parameter(description = "ID de l'offre d'emploi", required = true)
            @PathVariable UUID id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}