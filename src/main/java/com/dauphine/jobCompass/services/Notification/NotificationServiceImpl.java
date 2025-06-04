package com.dauphine.jobCompass.services.Notification;

import com.dauphine.jobCompass.dto.Notification.NotificationDto;
import com.dauphine.jobCompass.exceptions.NotFoundException;
import com.dauphine.jobCompass.mapper.NotificationMapper;
import com.dauphine.jobCompass.model.Notification;
import com.dauphine.jobCompass.model.User;
import com.dauphine.jobCompass.model.Application;
import com.dauphine.jobCompass.repositories.NotificationRepository;
import com.dauphine.jobCompass.services.Application.ApplicationService;
import com.dauphine.jobCompass.services.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserService userService;
    private final ApplicationService applicationService;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   NotificationMapper notificationMapper,
                                   UserService userService,
                                   ApplicationService applicationService) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @Override
    @Transactional
    public NotificationDto createNotification(NotificationDto notificationDto) throws NotFoundException {
        User candidate = userService.findById(notificationDto.getCandidateId())
                .orElseThrow(() -> new NotFoundException("Candidate not found"));
        User recruiter = userService.findById(notificationDto.getRecruiterId())
                .orElseThrow(() -> new NotFoundException("Recruiter not found"));
        Application application = applicationService.findById(notificationDto.getApplicationId())
                .orElseThrow(() -> new NotFoundException("Application not found"));

        Notification notification = new Notification();
        notification.setCandidate(candidate);
        notification.setRecruiter(recruiter);
        notification.setApplication(application);
        notification.setMessage(notificationDto.getMessage());
        notification.setIsRead(false);

        Notification savedNotification = notificationRepository.save(notification);
        return notificationMapper.toDto(savedNotification);
    }

    @Override
    public List<NotificationDto> getAllNotificationsByUserId(UUID userId) {
        return notificationRepository.findByCandidateIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> getUnreadNotificationsByUserId(UUID userId) {
        return notificationRepository.findByCandidateIdAndIsReadFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotificationDto updateNotificationReadStatus(UUID notificationId, boolean isRead) throws NotFoundException {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException("Notification not found"));
        notification.setIsRead(isRead);
        Notification updatedNotification = notificationRepository.save(notification);
        return notificationMapper.toDto(updatedNotification);
    }
}