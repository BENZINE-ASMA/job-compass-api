package com.dauphine.jobCompass.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ApplicationStatus {
    PENDING,
    ACCEPTED,
    REJECTED;

    @JsonValue
    public String getValue() {
        return this.name(); // Retourne "PENDING", "ACCEPTED", "REJECTED"
    }

    @JsonCreator
    public static ApplicationStatus fromString(String value) {
        if (value == null) {
            return PENDING;
        }

        try {
            return ApplicationStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid application status: " + value);
        }
    }

    @Override
    public String toString() {
        return this.name();
    }
}