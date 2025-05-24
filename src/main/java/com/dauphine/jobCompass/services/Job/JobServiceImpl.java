package com.dauphine.jobCompass.services.Job;

import com.dauphine.jobCompass.dto.Job.JobCreationRequest;
import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.dto.JobFilters.JobFilters;
import com.dauphine.jobCompass.exceptions.ResourceNotFoundException;
import com.dauphine.jobCompass.mapper.JobMapper;
import com.dauphine.jobCompass.model.Job;
import com.dauphine.jobCompass.model.User;
import com.dauphine.jobCompass.repositories.JobRepository;
import com.dauphine.jobCompass.repositories.UserRepository;
import com.dauphine.jobCompass.services.Company.CompanyService;
import com.dauphine.jobCompass.services.JobCategory.JobCategoryService;
import com.dauphine.jobCompass.services.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Cache;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final CompanyService companyService;
    private final JobCategoryService jobCategoryService;
    private final UserService userService;

    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper, CompanyService companyService, JobCategoryService jobCategoryService, UserService userService) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.companyService = companyService;
        this.jobCategoryService = jobCategoryService;
        this.userService = userService;

    }

    @Override
    public List<JobDTO> getAllJobs() {
        List<Job> jobs = this.jobRepository.findAll();
        return jobs.stream().map(jobMapper::toDto).collect(Collectors.toList());
    }

    public List<JobDTO> getFilteredJobs(JobFilters filters) {
        List<Job> jobs = this.jobRepository.findFilteredJobs(filters.getSearch(),filters.getCategory(),filters.getContractType(),filters.getLocation());
        return jobs.stream().map(jobMapper::toDto).collect(Collectors.toList());
    }
    @Override
    public JobDTO createJob(JobCreationRequest jobCreationRequest) {
        Job job = new Job();

        job.setOwner(userService.getById(jobCreationRequest.getOwnerId()));
        job.setCompany(companyService.getCompanyById(jobCreationRequest.getCompanyId()));
        job.setCategory(jobCategoryService.getJobCategoryById(jobCreationRequest.getCategoryId()));
        job.setTitle(jobCreationRequest.getTitle());
        job.setDescription(jobCreationRequest.getDescription());
        job.setJobType(jobCreationRequest.getJobType());
        job.setSalary(jobCreationRequest.getSalary());
        job.setLocation(jobCreationRequest.getLocation());
        job.setExpiryDate(jobCreationRequest.getExpiryDate());
        job.setCreatedAt(LocalDate.now());

        Job savedJob = jobRepository.save(job);

        return jobMapper.toDto(savedJob);
    }
    @Override
    public JobDTO getJobById(UUID id) {
        return jobRepository.findById(id)
                .map(jobMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
    }



}
