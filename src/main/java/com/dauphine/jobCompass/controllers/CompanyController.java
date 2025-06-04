package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.Company.CompanyCreationRequest;
import com.dauphine.jobCompass.dto.Company.CompanyDTO;
import com.dauphine.jobCompass.model.Company;
import com.dauphine.jobCompass.services.Company.CompanyService;
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

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Companies", description = "API pour la gestion des entreprises")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    @Operation(
            summary = "Récupérer toutes les entreprises",
            description = "Obtenir la liste complète de toutes les entreprises enregistrées"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des entreprises récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/companies/names")
    @Operation(
            summary = "Récupérer les noms de toutes les entreprises",
            description = "Obtenir uniquement les noms des entreprises pour les listes déroulantes"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des noms d'entreprises récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<String>> getAllCompaniesNames() {
        List<String> companyNames = companyService.getAllCompaniesNames();
        return ResponseEntity.ok(companyNames);
    }

    @PostMapping("/companies")
    @Operation(
            summary = "Créer une nouvelle entreprise",
            description = "Ajouter une nouvelle entreprise dans le système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entreprise créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données d'entreprise invalides"),
            @ApiResponse(responseCode = "409", description = "Une entreprise avec ce nom existe déjà"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<Company> createCompany(
            @Valid @RequestBody CompanyCreationRequest request) {
        Company company = companyService.createCompany(request);
        return ResponseEntity
                .created(URI.create("/api/v1/companies/" + company.getId()))
                .body(company);
    }

    @GetMapping("/companies/filter")
    @Operation(
            summary = "Filtrer les entreprises",
            description = "Rechercher des entreprises par nom et/ou localisation"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Résultats de recherche récupérés avec succès"),
            @ApiResponse(responseCode = "400", description = "Paramètres de recherche invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<CompanyDTO>> filterCompanies(
            @Parameter(description = "Terme de recherche pour le nom de l'entreprise")
            @RequestParam(required = false) String search,
            @Parameter(description = "Localisation de l'entreprise")
            @RequestParam(required = false) String location) {
        List<CompanyDTO> filteredCompanies = companyService.filterCompanies(search, location);
        return ResponseEntity.ok(filteredCompanies);
    }
}