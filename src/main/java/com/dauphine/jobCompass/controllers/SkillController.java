package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.Skill.SkillDTO;
import com.dauphine.jobCompass.dto.Skill.SkillRequest;
import com.dauphine.jobCompass.exceptions.ResourceNotFoundException;
import com.dauphine.jobCompass.exceptions.SkillAlreadyExistsException;
import com.dauphine.jobCompass.mapper.SkillMapper;
import com.dauphine.jobCompass.model.Skill;
import com.dauphine.jobCompass.repositories.SkillRepository;
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
@RequestMapping("/api/v1/skills")
@Tag(name = "Skills", description = "API pour la gestion des compétences")
public class SkillController {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    public SkillController(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @PostMapping
    @Operation(
            summary = "Créer une nouvelle compétence",
            description = "Ajouter une nouvelle compétence dans le système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Compétence créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données de compétence invalides"),
            @ApiResponse(responseCode = "409", description = "Une compétence avec ce nom existe déjà"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<SkillDTO> createSkill(
            @Parameter(description = "Données de la compétence à créer", required = true)
            @Valid @RequestBody SkillRequest request) {

        if (skillRepository.existsByName(request.getName())) {
            throw new SkillAlreadyExistsException(request.getName());
        }

        Skill skill = new Skill(request.getName(), request.isPredefined());
        Skill saved = skillRepository.save(skill);
        SkillDTO skillDTO = skillMapper.toDTO(saved);

        return ResponseEntity
                .created(URI.create("/api/v1/skills/" + saved.getId()))
                .body(skillDTO);
    }

    @GetMapping
    @Operation(
            summary = "Récupérer toutes les compétences",
            description = "Obtenir la liste complète de toutes les compétences disponibles"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des compétences récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<SkillDTO>> getAllSkills() {
        List<SkillDTO> skills = skillMapper.toDTOList(skillRepository.findAll());
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Récupérer une compétence par ID",
            description = "Obtenir les détails d'une compétence spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compétence trouvée"),
            @ApiResponse(responseCode = "400", description = "Format UUID invalide"),
            @ApiResponse(responseCode = "404", description = "Compétence non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<SkillDTO> getSkillById(
            @Parameter(description = "ID de la compétence", required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compétence non trouvée avec l'ID: " + id));

        return ResponseEntity.ok(skillMapper.toDTO(skill));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Mettre à jour une compétence",
            description = "Modifier une compétence existante"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compétence mise à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou format UUID incorrect"),
            @ApiResponse(responseCode = "404", description = "Compétence non trouvée"),
            @ApiResponse(responseCode = "409", description = "Une compétence avec ce nom existe déjà"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<SkillDTO> updateSkill(
            @Parameter(description = "ID de la compétence à mettre à jour", required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id,
            @Parameter(description = "Nouvelles données de la compétence", required = true)
            @Valid @RequestBody SkillRequest request) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compétence non trouvée avec l'ID: " + id));

        // Vérifier si le nouveau nom existe déjà (sauf si c'est le même skill)
        if (!skill.getName().equals(request.getName()) && skillRepository.existsByName(request.getName())) {
            throw new SkillAlreadyExistsException(request.getName());
        }

        skill.setName(request.getName());
        skill.setPredefined(request.isPredefined());
        Skill updated = skillRepository.save(skill);

        return ResponseEntity.ok(skillMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprimer une compétence",
            description = "Supprimer définitivement une compétence du système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Compétence supprimée avec succès"),
            @ApiResponse(responseCode = "400", description = "Format UUID invalide"),
            @ApiResponse(responseCode = "404", description = "Compétence non trouvée"),
            @ApiResponse(responseCode = "409", description = "Impossible de supprimer - compétence utilisée ailleurs"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<Void> deleteSkill(
            @Parameter(description = "ID de la compétence à supprimer", required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id) {

        if (!skillRepository.existsById(id)) {
            throw new ResourceNotFoundException("Compétence non trouvée avec l'ID: " + id);
        }

        skillRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
 /*
    @GetMapping("/predefined")
    @Operation(
            summary = "Récupérer les compétences prédéfinies",
            description = "Obtenir uniquement les compétences prédéfinies du système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des compétences prédéfinies récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<SkillDTO>> getPredefinedSkills() {
        List<Skill> predefinedSkills = skillRepository.findByPredefinedTrue();
        List<SkillDTO> skillDTOs = skillMapper.toDTOList(predefinedSkills);
        return ResponseEntity.ok(skillDTOs);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Rechercher des compétences par nom",
            description = "Rechercher des compétences contenant le terme spécifié"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Résultats de recherche récupérés avec succès"),
            @ApiResponse(responseCode = "400", description = "Paramètre de recherche invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
   public ResponseEntity<List<SkillDTO>> searchSkillsByName(
            @Parameter(description = "Terme de recherche pour le nom de la compétence", required = true)
            @RequestParam String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le terme de recherche ne peut pas être vide");
        }

        List<Skill> skills = skillRepository.findByNameContainingIgnoreCase(name.trim());
        List<SkillDTO> skillDTOs = skillMapper.toDTOList(skills);
        return ResponseEntity.ok(skillDTOs);
    }

   */
}