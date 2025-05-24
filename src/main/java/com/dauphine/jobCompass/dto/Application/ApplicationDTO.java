package com.dauphine.jobCompass.dto.Application;

import com.dauphine.jobCompass.dto.Job.JobDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApplicationDTO {
    private UUID id;
    private JobDTO job;
    private UUID userId;
    private String coverLetter;
    private String resumeUrl;
    private String status;
    private LocalDateTime createdAt;

    public ApplicationDTO() {

    }

    public ApplicationDTO(UUID id, JobDTO job, UUID userId, String coverLetter,
                          String resumeUrl, String status, LocalDateTime createdAt) {
        this.id = id;
        this.job = job;
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

    public JobDTO getJob() {
        return job;
    }

    public void setJob(JobDTO job) {
        this.job = job;
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
