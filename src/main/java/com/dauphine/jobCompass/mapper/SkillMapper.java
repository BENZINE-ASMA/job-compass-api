package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.Skill.SkillDTO;
import com.dauphine.jobCompass.model.Skill;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SkillMapper {

    public SkillDTO toDTO(Skill skill) {
        return new SkillDTO(skill.getId(), skill.getName(), skill.isPredefined());
    }

    public List<SkillDTO> toDTOList(List<Skill> skills) {
        return skills.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Set<SkillDTO> toDTOSet(Set<Skill> skills) {
        return skills.stream().map(this::toDTO).collect(Collectors.toSet());
    }
}
