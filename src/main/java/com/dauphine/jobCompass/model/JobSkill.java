package com.dauphine.jobCompass.model;

import jakarta.persistence.*;


import java.io.Serializable;
import java.util.UUID;

// Classe pour la clé composée
@Embeddable
class JobSkillId implements Serializable {
    @Column(name = "job_id")
    private UUID jobId;

    @Column(name = "skill_id")
    private UUID skillId;

    public JobSkillId() {}

    public JobSkillId(UUID jobId, UUID skillId) {
        this.jobId = jobId;
        this.skillId = skillId;
    }

    // Equals et hashCode sont essentiels pour une clé composée
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobSkillId that = (JobSkillId) o;
        return jobId.equals(that.jobId) && skillId.equals(that.skillId);
    }

    @Override
    public int hashCode() {
        return 31 * jobId.hashCode() + skillId.hashCode();
    }
}

// Entité principale
@Entity
@Table(name = "job_required_skills")
public class JobSkill {
    @EmbeddedId
    private JobSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("jobId")  // Référence à l'attribut dans JobSkillId
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")  // Référence à l'attribut dans JobSkillId
    @JoinColumn(name = "skill_id")
    private Skill skill;

    // Constructeur pratique
    public JobSkill(Job job, Skill skill) {
        this.job = job;
        this.skill = skill;
        this.id = new JobSkillId(job.getId(), skill.getId());
    }

    public JobSkill() {

    }
}