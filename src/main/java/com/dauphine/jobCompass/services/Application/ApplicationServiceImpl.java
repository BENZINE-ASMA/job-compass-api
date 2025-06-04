package com.dauphine.jobCompass.services.Application;


import com.dauphine.jobCompass.dto.Application.ApplicationDTO;
import com.dauphine.jobCompass.dto.Application.ApplicationRequestDTO;
import com.dauphine.jobCompass.dto.Notification.NotificationDto;
import com.dauphine.jobCompass.dto.User.SimpleUserDTO;
import com.dauphine.jobCompass.exceptions.AlreadyAppliedException;
import com.dauphine.jobCompass.exceptions.ApplicationNotFoundException;
import com.dauphine.jobCompass.exceptions.JobNotFoundException;
import com.dauphine.jobCompass.exceptions.NotFoundException;
import com.dauphine.jobCompass.exceptions.UserNotFoundException;
import com.dauphine.jobCompass.mapper.ApplicationMapper;
import com.dauphine.jobCompass.mapper.JobMapper;
import com.dauphine.jobCompass.mapper.UserMapper;
import com.dauphine.jobCompass.model.Application;
import com.dauphine.jobCompass.model.Job;
import com.dauphine.jobCompass.model.User;
import com.dauphine.jobCompass.model.enums.ApplicationStatus;
import com.dauphine.jobCompass.repositories.ApplicationRepository;
import com.dauphine.jobCompass.repositories.JobRepository;
import com.dauphine.jobCompass.repositories.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final ApplicationMapper applicationMapper;

    public ApplicationServiceImpl(
            ApplicationRepository applicationRepository,
            JobRepository jobRepository,
            UserRepository userRepository,
            ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.applicationMapper = applicationMapper;
    }


    @Override
    public Optional<Application> findById(UUID applicationId) {
        return applicationRepository.findById(applicationId);
    }

    @Override
    public ApplicationDTO applyToJob(ApplicationRequestDTO dto) {
        UUID userId = dto.getUserId();
        UUID jobId = dto.getJobId();

        if (applicationRepository.existsByUserIdAndJobId(userId, jobId)) {
            throw new AlreadyAppliedException();
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(JobNotFoundException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Application app = new Application();
        app.setId(UUID.randomUUID());
        app.setJob(job);
        app.setUser(user);
        app.setCoverLetter(dto.getCoverLetter());
        app.setResumeUrl(dto.getResumeUrl());
        app.setStatus(ApplicationStatus.PENDING); // ✅ Utilise enum
        app.setCreatedAt(LocalDateTime.now());

        Application saved = applicationRepository.save(app);
        return applicationMapper.toDto(saved);
    }

    @Override
    public List<ApplicationDTO> getApplicationsByUserId(UUID userId) {
        List<Application> applications = applicationRepository.findByUserId(userId);
        return applicationMapper.toDtoList(applications);
    }
    @Override
    public List<ApplicationDTO> getApplicantsByJobId(UUID jobId) {
        List<Application> applications = applicationRepository.findByJobId(jobId);
        return applicationMapper.toDtoList(applications);
    }

    @Override
    public ApplicationDTO updateApplicationStatus(UUID applicationId, ApplicationStatus status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found: " + applicationId));

        application.setStatus(status);
        Application saved = applicationRepository.save(application);

        return applicationMapper.toDto(saved);
    }
}