package com.dauphine.jobCompass.dto.Notification;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationDto {
    private UUID id;
    private UUID candidateId;
    private UUID recruiterId;
    private UUID applicationId;
    private String applicationTitle;
    private String message;
    private boolean read;  // Simplifié: read au lieu de isRead

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // Constructeurs
    public NotificationDto() {
    }

    public NotificationDto(UUID id, UUID candidateId, UUID recruiterId,
                           UUID applicationId, String applicationTitle,
                           String message, boolean read, LocalDateTime createdAt) {
        this.id = id;
        this.candidateId = candidateId;
        this.recruiterId = recruiterId;
        this.applicationId = applicationId;
        this.applicationTitle = applicationTitle;
        this.message = message;
        this.read = read;
        this.createdAt = createdAt;
    }

    // Getters et Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(UUID candidateId) {
        this.candidateId = candidateId;
    }

    public UUID getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(UUID recruiterId) {
        this.recruiterId = recruiterId;
    }

    public UUID getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(UUID applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationTitle() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle = applicationTitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {  // Convention: isRead() pour boolean
        return read;
    }

    public void setRead(boolean read) {  // setRead() cohérent avec le champ
        this.read = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}