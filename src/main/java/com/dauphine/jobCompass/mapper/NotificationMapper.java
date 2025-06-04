package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.Notification.NotificationDto;
import com.dauphine.jobCompass.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ApplicationMapper.class})
public interface NotificationMapper {

    @Mapping(source = "candidate.id", target = "candidateId")
    @Mapping(source = "recruiter.id", target = "recruiterId")
    @Mapping(source = "application.id", target = "applicationId")
    @Mapping(source = "application.job.title", target = "applicationTitle")
    NotificationDto toDto(Notification notification);

    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "recruiter", ignore = true)
    @Mapping(target = "application", ignore = true)
    Notification toEntity(NotificationDto notificationDTO);
}