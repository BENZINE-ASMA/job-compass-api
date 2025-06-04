package com.dauphine.jobCompass.services.Application;


import com.dauphine.jobCompass.dto.Application.ApplicationDTO;
import com.dauphine.jobCompass.dto.Application.ApplicationRequestDTO;
import com.dauphine.jobCompass.dto.User.SimpleUserDTO;
import com.dauphine.jobCompass.model.Application;
import com.dauphine.jobCompass.model.enums.ApplicationStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApplicationService {
    Optional<Application> findById(UUID applicationId);
    ApplicationDTO applyToJob(ApplicationRequestDTO dto);

    List<ApplicationDTO> getApplicationsByUserId(UUID userId);

    List<ApplicationDTO> getApplicantsByJobId(UUID jobId);

    ApplicationDTO updateApplicationStatus(UUID applicationId, ApplicationStatus status);

    ApplicationDTO getApplicationById(UUID applicationId);
}
