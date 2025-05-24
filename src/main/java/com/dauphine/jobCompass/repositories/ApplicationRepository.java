package com.dauphine.jobCompass.repositories;

import com.dauphine.jobCompass.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    boolean existsByUserIdAndJobId(UUID userId, UUID jobId);
    List<Application> findByUserId(UUID userId);
    List<Application> findByJobId(UUID jobId);

}
