package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.Skill.SkillDTO;
import com.dauphine.jobCompass.dto.Skill.SkillRequest;
import com.dauphine.jobCompass.mapper.SkillMapper;
import com.dauphine.jobCompass.model.Skill;
import com.dauphine.jobCompass.repositories.SkillRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/skills")
public class SkillController {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    public SkillController(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @PostMapping
    public ResponseEntity<SkillDTO> createSkill(@RequestBody SkillRequest request) {
        if (skillRepository.existsByName(request.getName())) {
            return ResponseEntity.badRequest().build();
        }
        Skill skill = new Skill(request.getName(), request.isPredefined());
        Skill saved = skillRepository.save(skill);
        return ResponseEntity.ok(skillMapper.toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getAllSkills() {
        List<SkillDTO> dtos = skillMapper.toDTOList(skillRepository.findAll());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getSkillById(@PathVariable UUID id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Skill not found"));
        return ResponseEntity.ok(skillMapper.toDTO(skill));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillDTO> updateSkill(@PathVariable UUID id, @RequestBody SkillRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Skill not found"));

        skill.setName(request.getName());
        skill.setPredefined(request.isPredefined());
        Skill updated = skillRepository.save(skill);

        return ResponseEntity.ok(skillMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable UUID id) {
        if (!skillRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        skillRepository.deleteById(id);
        return ResponseEntity.ok("Skill deleted");
    }
}
