package com.dauphine.jobCompass.repositories;

import com.dauphine.jobCompass.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {

}
