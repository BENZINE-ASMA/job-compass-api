package com.dauphine.jobCompass.dto;

import java.time.LocalDateTime;

public class ApplicationDTO {
    private Integer id;
    private SimpleJobDTO job;
    private SimpleUserDTO user;
    private String coverLetter;
    private String resumeUrl;
    private String status;
    private LocalDateTime createdAt;

    // Getters et setters

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public SimpleJobDTO getJob() {
        return job;
    }
    public void setJob(SimpleJobDTO job) {
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
