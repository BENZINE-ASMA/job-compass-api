package com.dauphine.jobCompass.services.skill;

import com.dauphine.jobCompass.dto.Skill.SkillDTO;
import com.dauphine.jobCompass.mapper.SkillMapper;
import com.dauphine.jobCompass.model.Job;
import com.dauphine.jobCompass.model.Skill;
import com.dauphine.jobCompass.repositories.JobRepository;
import com.dauphine.jobCompass.repositories.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class JobSkillServiceImpl implements JobSkillService {

    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    public JobSkillServiceImpl(JobRepository jobRepository, SkillRepository skillRepository, SkillMapper skillMapper) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Override
    @Transactional
    public void addSkillsToJob(UUID jobId, List<UUID> skillIds) {
        if (skillIds == null || skillIds.isEmpty()) {
            throw new IllegalArgumentException("Skill IDs list cannot be null or empty");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

        List<Skill> skills = skillRepository.findAllById(skillIds);

        if (skills.size() != skillIds.size()) {
            throw new IllegalArgumentException("Some skills were not found");
        }
        Set<Skill> currentSkills = job.getRequiredSkills();
        List<Skill> newSkills = skills.stream()
                .filter(skill -> !currentSkills.contains(skill))
                .toList();

        if (newSkills.isEmpty()) {
            throw new IllegalArgumentException("All provided skills are already associated with this job");
        }

        job.getRequiredSkills().addAll(newSkills);
        jobRepository.save(job);
    }

    @Override
    @Transactional
    public void addSkillsToJob(UUID jobId, List<UUID> skillIds, List<String> skillNames) {
        // Ajouter les compétences existantes par ID
        if (skillIds != null && !skillIds.isEmpty()) {
            for (UUID skillId : skillIds) {
                // Votre logique d'ajout par ID
                addExistingSkillToJob(jobId, skillId);
            }
        }

        // Ajouter les nouvelles compétences par nom
        if (skillNames != null && !skillNames.isEmpty()) {
            for (String skillName : skillNames) {
                // Votre logique d'ajout par nom
                addNewSkillToJob(jobId, skillName);
            }
        }
    }


    private void addExistingSkillToJob(UUID jobId, UUID skillId) {
        if (skillId == null) {
            throw new IllegalArgumentException("Skill ID cannot be null");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new IllegalArgumentException("Skill not found with ID: " + skillId));

        // Vérifier si la compétence n'est pas déjà associée au job
        if (job.getRequiredSkills().contains(skill)) {
            throw new IllegalArgumentException("Job already has this skill: " + skill.getName());
        }

        // Ajouter la compétence au job
        job.getRequiredSkills().add(skill);
        jobRepository.save(job);
    }
    private void addNewSkillToJob(UUID jobId, String skillName) {
        if (skillName == null || skillName.trim().isEmpty()) {
            throw new IllegalArgumentException("Skill name cannot be null or empty");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

        String trimmedSkillName = skillName.trim();

        // Vérifier si une compétence avec ce nom existe déjà
        Optional<Skill> existingSkill = skillRepository.findByNameIgnoreCase(trimmedSkillName);

        Skill skill;
        if (existingSkill.isPresent()) {
            // Si la compétence existe déjà, l'utiliser
            skill = existingSkill.get();
        } else {
            // Sinon, créer une nouvelle compétence
            skill = new Skill();
            skill.setName(trimmedSkillName);
            skill.setPredefined(false);
            skill = skillRepository.save(skill);
        }

        // Vérifier si la compétence n'est pas déjà associée au job
        if (job.getRequiredSkills().contains(skill)) {
            throw new IllegalArgumentException("Job already has this skill: " + trimmedSkillName);
        }

        // Ajouter la compétence au job
        job.getRequiredSkills().add(skill);
        jobRepository.save(job);
    }

    @Override
    @Transactional
    public void removeSkillFromJob(UUID jobId, UUID skillId) {
        if (skillId == null) {
            throw new IllegalArgumentException("Skill ID cannot be null");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new IllegalArgumentException("Skill not found with ID: " + skillId));

        if (!job.getRequiredSkills().contains(skill)) {
            throw new IllegalArgumentException("Job does not have this skill");
        }

        job.getRequiredSkills().remove(skill);
        jobRepository.save(job);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Skill> getSkillsForJob(UUID jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

        return new HashSet<>(job.getRequiredSkills());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean jobHasSkill(UUID jobId, UUID skillId) {
        if (skillId == null) {
            return false;
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

        return job.getRequiredSkills().stream()
                .anyMatch(skill -> skill.getId().equals(skillId));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean jobHasSkillByName(UUID jobId, String skillName) {
        if (skillName == null || skillName.trim().isEmpty()) {
            return false;
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

        String trimmedSkillName = skillName.trim();
        return job.getRequiredSkills().stream()
                .anyMatch(skill -> skill.getName().equalsIgnoreCase(trimmedSkillName));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillDTO> getSkillsDTOForJob(UUID jobId) {
        Set<Skill> skills = getSkillsForJob(jobId);
        return skills.stream()
                .map(skillMapper::toDTO)
                .sorted(Comparator.comparing(SkillDTO::getName))
                .toList();
    }

    @Override
    @Transactional
    public void removeAllSkillsFromJob(UUID jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

        job.getRequiredSkills().clear();
        jobRepository.save(job);
    }

    @Override
    @Transactional(readOnly = true)
    public long countSkillsForJob(UUID jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

        return job.getRequiredSkills().size();
    }
}