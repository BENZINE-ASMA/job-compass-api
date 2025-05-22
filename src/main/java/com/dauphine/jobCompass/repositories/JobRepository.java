package com.dauphine.jobCompass.repositories;

import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    @Query("SELECT j FROM Job j WHERE " +
            "(:search IS NULL OR j.title LIKE %:search% OR j.description LIKE %:search%) AND " +
            "(:category IS NULL OR j.category.name = :category) AND " +
            "(:contractType IS NULL OR j.jobType = :contractType) AND " +
            "(:location IS NULL OR j.location LIKE %:location%)")
    List<Job> findFilteredJobs(
            @Param("search") String search,
            @Param("category") String category,
            @Param("contractType") String contractType,
            @Param("location") String location);

}
