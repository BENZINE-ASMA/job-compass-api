package com.dauphine.jobCompass.repositories;

import com.dauphine.jobCompass.model.Application;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    boolean existsByUserIdAndJobId(UUID userId, UUID jobId);
    List<Application> findByUserId(UUID userId);
    List<Application> findByJobId(UUID jobId);

    @Modifying
    @Transactional
    @Query("UPDATE Application a SET a.status = :status WHERE a.id = :applicationId")
    void updateApplicationStatus(@Param("applicationId") UUID applicationId,
                                 @Param("status") String status);
}
