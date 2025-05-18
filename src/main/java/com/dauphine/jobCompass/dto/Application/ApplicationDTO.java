package com.dauphine.jobCompass.dto.Application;

import com.dauphine.jobCompass.dto.User.SimpleUserDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApplicationDTO {
    private UUID id;
    private ApplicationRequest.SimpleJobDTO job;
    private SimpleUserDTO user;
    private String coverLetter;
    private String resumeUrl;
    private String status;
    private LocalDateTime createdAt;

    // Getters et setters

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public ApplicationRequest.SimpleJobDTO getJob() {
        return job;
    }
    public void setJob(ApplicationRequest.SimpleJobDTO job) {
        this.job = job;
    }
    public SimpleUserDTO getUser() {
        return user;
    }
    public void setUser(SimpleUserDTO user) {
        this.user = user;
    }
    public String getCoverLetter() {
        return coverLetter;
    }
    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }
    public String getResumeUrl() {
        return resumeUrl;
    }
    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
