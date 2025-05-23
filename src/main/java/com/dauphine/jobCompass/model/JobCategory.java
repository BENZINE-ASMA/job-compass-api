package com.dauphine.jobCompass.model;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "job_categories")
public class JobCategory {
    @Id
    @Column(name = "category_id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}