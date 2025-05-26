package com.dauphine.jobCompass.model.enums;

public enum ApplicationStatus {
    PENDING,
    ACCEPTED,
    REJECTED;
    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}