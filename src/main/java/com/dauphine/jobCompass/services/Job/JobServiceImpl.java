package com.dauphine.jobCompass.services.Job;

import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.dto.JobFilters.JobFilters;
import com.dauphine.jobCompass.mapper.JobMapper;
import com.dauphine.jobCompass.model.Job;
import com.dauphine.jobCompass.model.User;
import com.dauphine.jobCompass.repositories.JobRepository;
import com.dauphine.jobCompass.repositories.UserRepository;
import com.dauphine.jobCompass.services.Category.CategoryService;
import com.dauphine.jobCompass.services.Company.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Cache;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final CompanyService companyService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;

    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper, CompanyService companyService, CategoryService categoryService, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.companyService = companyService;
        this.categoryService = categoryService;
        this.userRepository = userRepository;
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
    public Job createJob(JobDTO jobDTO) {
        Job job = new Job();
        job.setOwner(jobDTO.getOwner());
        job.setCompany(companyService.getCompanyById(jobDTO.getCompanyId()));
        job.setCategory(categoryService.getCategoryById(jobDTO.getCategoryId()));
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setJobType(jobDTO.getJobType());
        job.setSalary(jobDTO.getSalary());
        job.setLocation(jobDTO.getLocation());
        job.setExpiryDate(jobDTO.getExpiryDate());

        return jobRepository.save(job);
    }


}
