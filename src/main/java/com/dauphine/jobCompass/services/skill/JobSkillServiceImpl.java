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
    public SkillDTO addSkillToJobByName(UUID jobId, String skillName) {
        if (skillName == null || skillName.trim().isEmpty()) {
            throw new IllegalArgumentException("Skill name cannot be null or empty");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

        String trimmedSkillName = skillName.trim();

        Optional<Skill> existingSkill = skillRepository.findByNameIgnoreCase(trimmedSkillName);

        Skill skill;
        if (existingSkill.isPresent()) {
            skill = existingSkill.get();
        } else {
            skill = new Skill();
            skill.setName(trimmedSkillName);
            skill.setPredefined(false);
            skill = skillRepository.save(skill);
        }

        if (job.getRequiredSkills().contains(skill)) {
            throw new IllegalArgumentException("Job already has this skill: " + trimmedSkillName);
        }
        job.getRequiredSkills().add(skill);
        jobRepository.save(job);
        return skillMapper.toDTO(skill);
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
    public void addSkillToJob(UUID jobId, UUID skillId) {
        if (skillId == null) {
            throw new IllegalArgumentException("Skill ID cannot be null");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new IllegalArgumentException("Skill not found with ID: " + skillId));

        if (job.getRequiredSkills().contains(skill)) {
            throw new IllegalArgumentException("Job already has this skill");
        }

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