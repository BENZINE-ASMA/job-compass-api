package com.dauphine.jobCompass.dto.Skill;

import java.util.UUID;

public class SkillDTO {
    private UUID id;
    private String name;
    private boolean isPredefined = false;;

    public SkillDTO() {}

    public SkillDTO(UUID id, String name, boolean isPredefined) {
        this.id = id;
        this.name = name;
        this.isPredefined = isPredefined;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPredefined() {
        return isPredefined;
    }

    public void setPredefined(boolean predefined) {
        isPredefined = predefined;
    }
}
