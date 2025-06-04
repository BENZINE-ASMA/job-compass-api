package com.dauphine.jobCompass.dto.Application;

import com.dauphine.jobCompass.model.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateApplicationStatusDTO {

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    public UpdateApplicationStatusDTO() {}

    public UpdateApplicationStatusDTO(ApplicationStatus status) {
        this.status = status;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UpdateApplicationStatusDTO{status=" + status + "}";
    }
}