package com.dauphine.jobCompass.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @Column(name = "skill_id", columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @ManyToMany(mappedBy = "requiredSkills")
    private Set<Job> jobs = new HashSet<>();

    public Set<Job> getJobs() {
        return jobs;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }
}