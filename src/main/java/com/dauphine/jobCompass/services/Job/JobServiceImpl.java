package com.dauphine.jobCompass.services.Job;

import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.mapper.JobMapper;
import com.dauphine.jobCompass.model.Job;
import com.dauphine.jobCompass.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }

    @Override
    public List<JobDTO> getAllJobs() {
        List<Job> jobs = this.jobRepository.findAll();
        return jobs.stream().map(jobMapper::toDto).collect(Collectors.toList());
    }
}
