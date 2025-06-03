package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.Skill.SkillDTO;
import com.dauphine.jobCompass.model.Skill;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    SkillDTO toDTO(Skill skill);

    List<SkillDTO> toDTOList(List<Skill> skills);

    Set<SkillDTO> toDTOSet(Set<Skill> skills);

    Skill toEntity(SkillDTO skillDTO);


}