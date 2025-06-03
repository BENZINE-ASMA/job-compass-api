package com.dauphine.jobCompass.dto.Skill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddSkillsToJobRequest {

    @Schema(description = "Liste des IDs des compétences existantes à ajouter", example = "[\"3fa85f64-5717-4562-b3fc-2c963f66afa6\"]")
    private List<UUID> skillIds;

    @Schema(description = "Liste des noms des nouvelles compétences à créer", example = "[\"React\", \"Node.js\"]")
    private List<String> skillNames;

    // Constructeurs
    public AddSkillsToJobRequest() {}

    public AddSkillsToJobRequest(List<UUID> skillIds, List<String> skillNames) {
        this.skillIds = skillIds != null ? skillIds : new ArrayList<>();
        this.skillNames = skillNames != null ? skillNames : new ArrayList<>();
    }

    // Getters et setters normaux
    public List<UUID> getSkillIds() {
        return skillIds != null ? skillIds : new ArrayList<>();
    }

    public void setSkillIds(List<UUID> skillIds) {
        this.skillIds = skillIds;
    }

    public List<String> getSkillNames() {
        return skillNames != null ? skillNames : new ArrayList<>();
    }

    public void setSkillNames(List<String> skillNames) {
        this.skillNames = skillNames;
    }

    // Méthodes utilitaires - IGNORÉES par Jackson et Swagger
    @JsonIgnore
    @Schema(hidden = true)
    public boolean hasSkillsToAdd() {
        return (!getSkillIds().isEmpty()) || (!getSkillNames().isEmpty());
    }

    @JsonIgnore
    @Schema(hidden = true)
    public int getTotalSkillsCount() {
        return getSkillIds().size() + getSkillNames().size();
    }

    @Override
    public String toString() {
        return "AddSkillsToJobRequest{" +
                "skillIds=" + skillIds +
                ", skillNames=" + skillNames +
                '}';
    }
}