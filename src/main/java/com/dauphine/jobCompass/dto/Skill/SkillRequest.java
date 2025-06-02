package com.dauphine.jobCompass.dto.Skill;

public class SkillRequest {
    private String name;
    private boolean isPredefined = true;

    public SkillRequest() {}

    public SkillRequest(String name, boolean isPredefined) {
        this.name = name;
        this.isPredefined = isPredefined;
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
