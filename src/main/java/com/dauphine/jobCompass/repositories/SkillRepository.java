package com.dauphine.jobCompass.repositories;

import com.dauphine.jobCompass.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SkillRepository extends JpaRepository<Skill, UUID> {
    Optional<Skill> findByNameIgnoreCase(String name);

    boolean existsByName(String name);
    boolean existsByNameIgnoreCase(String name);
}
