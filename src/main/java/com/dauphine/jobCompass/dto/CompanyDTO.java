package com.dauphine.jobCompass.dto;

import java.time.LocalDateTime;

public class CompanyDTO {
    private Long id;
    private String name;
    private String description;
    private String location;
    private String website;
    private LocalDateTime createdAt;
    private SimpleUserDTO owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SimpleUserDTO getOwner() {
        return owner;
    }

    public void setOwner(SimpleUserDTO owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
