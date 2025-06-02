package com.dauphine.jobCompass.services.skill;

import com.dauphine.jobCompass.dto.Skill.SkillDTO;
import com.dauphine.jobCompass.model.Skill;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface JobSkillService {
    void addSkillsToJob(UUID jobId, List<UUID> skillIds);
    void removeSkillFromJob(UUID jobId, UUID skillId);
    Set<Skill> getSkillsForJob(UUID jobId);
    public long countSkillsForJob(UUID jobId);
    public SkillDTO addSkillToJobByName(UUID jobId, String skillName);
    public void addSkillToJob(UUID jobId, UUID skillId);
    public boolean jobHasSkill(UUID jobId, UUID skillId);
    public boolean jobHasSkillByName(UUID jobId, String skillName);
    public List<SkillDTO> getSkillsDTOForJob(UUID jobId);
    public void removeAllSkillsFromJob(UUID jobId);
}