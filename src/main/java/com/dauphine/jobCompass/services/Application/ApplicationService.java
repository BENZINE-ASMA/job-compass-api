package com.dauphine.jobCompass.services.Application;


import com.dauphine.jobCompass.dto.Application.ApplicationDTO;
import com.dauphine.jobCompass.dto.Application.ApplicationRequestDTO;
import com.dauphine.jobCompass.dto.User.SimpleUserDTO;
import com.dauphine.jobCompass.model.Application;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {
    ApplicationDTO applyToJob(ApplicationRequestDTO dto);
    List<ApplicationDTO> getApplicationsByUserId(UUID userId);
    List<SimpleUserDTO> getApplicantsByJobId(UUID jobId);

}
