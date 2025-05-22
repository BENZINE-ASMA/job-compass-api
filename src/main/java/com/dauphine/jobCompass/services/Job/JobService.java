package com.dauphine.jobCompass.services.Job;

import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.dto.JobFilters.JobFilters;

import java.util.List;

public interface JobService {

List<JobDTO> getAllJobs ();
List<JobDTO> getFilteredJobs(JobFilters filters);
}
