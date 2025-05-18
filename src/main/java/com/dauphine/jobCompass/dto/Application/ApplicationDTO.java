package com.dauphine.jobCompass.dto.Application;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApplicationDTO {
    private UUID id;
    private UUID jobId;
    private UUID userId;
    private String coverLetter;
    private String resumeUrl;
    private String status;
    private LocalDateTime createdAt;

    public ApplicationDTO() {

    }

    public ApplicationDTO(UUID id, UUID jobId, UUID userId, String coverLetter,
                          String resumeUrl, String status, LocalDateTime createdAt) {
        this.id = id;
        this.jobId = jobId;
        this.userId = userId;
        this.coverLetter = coverLetter;
        this.resumeUrl = resumeUrl;
        this.status = status;
        this.createdAt = createdAt;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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
