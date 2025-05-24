package com.dauphine.jobCompass.services.Job;

import com.dauphine.jobCompass.dto.Job.JobCreationRequest;
import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.dto.JobFilters.JobFilters;
import com.dauphine.jobCompass.model.Job;

import java.util.List;
import java.util.UUID;

public interface JobService {

List<JobDTO> getAllJobs ();
List<JobDTO> getFilteredJobs(JobFilters filters);
JobDTO createJob(JobCreationRequest jobCreationRequest);
JobDTO getJobById (UUID id);
}
