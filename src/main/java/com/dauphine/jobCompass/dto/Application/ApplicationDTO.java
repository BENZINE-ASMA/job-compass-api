package com.dauphine.jobCompass.dto.Application;

import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.dto.User.UserDTO;
import com.dauphine.jobCompass.model.enums.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApplicationDTO {
    private UUID id;
    private JobDTO job;
    private UserDTO user;
    private String coverLetter;
    private String resumeUrl;
    private ApplicationStatus status; // ✅ Changé de String à ApplicationStatus

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public ApplicationDTO() {}

    public ApplicationDTO(UUID id, JobDTO job, UserDTO user, String coverLetter,
                          String resumeUrl, ApplicationStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.job = job;
        this.user = user;
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
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

    // ✅ Getter/Setter modifiés pour ApplicationStatus
    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ApplicationDTO{" +
                "id=" + id +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}