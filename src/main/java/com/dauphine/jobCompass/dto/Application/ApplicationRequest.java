package com.dauphine.jobCompass.dto.Application;

import java.util.UUID;

public class ApplicationRequest {

    private UUID jobId;
    private UUID userId;
    private String coverLetter;
    private String resumeUrl;

    public ApplicationRequest() {

    }

    public ApplicationRequest(UUID jobId, UUID userId, String coverLetter, String resumeUrl) {
        this.jobId = jobId;
        this.userId = userId;
        this.coverLetter = coverLetter;
        this.resumeUrl = resumeUrl;
    }

    // Getters et setters

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
}
