package com.dauphine.jobCompass.services.Job;

import com.dauphine.jobCompass.dto.Job.JobCreationRequest;
import com.dauphine.jobCompass.dto.Job.JobDTO;
import com.dauphine.jobCompass.dto.JobFilters.JobFilters;
import com.dauphine.jobCompass.exceptions.ResourceNotFoundException;
import com.dauphine.jobCompass.mapper.JobMapper;
import com.dauphine.jobCompass.model.Company;
import com.dauphine.jobCompass.model.Job;
import com.dauphine.jobCompass.model.JobCategory;
import com.dauphine.jobCompass.repositories.CategoryRepository;
import com.dauphine.jobCompass.repositories.JobRepository;
import com.dauphine.jobCompass.services.Company.CompanyService;
import com.dauphine.jobCompass.services.JobCategory.JobCategoryService;
import com.dauphine.jobCompass.services.user.UserService;
import jdk.jfr.Category;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final CompanyService companyService;
    private final JobCategoryService jobCategoryService;
    private final UserService userService;
    private final CategoryRepository categoryRepository;

    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper, CompanyService companyService, JobCategoryService jobCategoryService, UserService userService, CategoryRepository categoryRepository) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.companyService = companyService;
        this.jobCategoryService = jobCategoryService;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<JobDTO> getAllJobs() {
        List<Job> jobs = this.jobRepository.findAll();
        return jobs.stream().map(jobMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<JobDTO> getAllOwnersJobs(UUID id){
        List<Job> jobs = this.jobRepository.findByOwnerId(id);
        return jobs.stream().map(jobMapper::toDto).collect(Collectors.toList());
    }
    @Override
    public List<JobDTO> getFilteredJobs(JobFilters filters) {
        List<Job> jobs = this.jobRepository.findFilteredJobs(filters.getSearch(),filters.getCategory(),filters.getContractType(),filters.getLocation());
        return jobs.stream().map(jobMapper::toDto).collect(Collectors.toList());
    }
    @Override
    public List<JobDTO> getFilteredJobsByOwnerId(JobFilters filters) {
        List<Job> jobs = this.jobRepository.findFilteredJobsByOwner(filters.getSearch(),filters.getCategory(),filters.getContractType(),filters.getLocation(), filters.getOwnerId());
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
    @Override
    public List<String> getAllLocations() {
        return jobRepository.findAll().stream()
                .map(Job::getLocation)
                .filter(loc -> loc != null && !loc.isBlank())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public JobDTO updateJob(UUID id, JobCreationRequest request) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));

        applyIfChanged(request.getTitle(), job.getTitle(), job::setTitle);
        applyIfChanged(request.getDescription(), job.getDescription(), job::setDescription);
        applyIfChanged(request.getSalary(), job.getSalary(), job::setSalary);
        applyIfChanged(request.getJobType(), job.getJobType(), job::setJobType);
        applyIfChanged(request.getLocation(), job.getLocation(), job::setLocation);
        applyIfChanged(request.getExpiryDate(), job.getExpiryDate(), job::setExpiryDate);

        if (request.getCategoryId() != null &&
                (job.getCategory() == null || !request.getCategoryId().equals(job.getCategory().getId()))) {
            JobCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            job.setCategory(category);
        }

        Job updatedJob = jobRepository.save(job);
        return jobMapper.toDto(updatedJob);
    }

    private <T> void applyIfChanged(T newValue, T oldValue, Consumer<T> setter) {
        if (newValue != null && !newValue.equals(oldValue)) {
            setter.accept(newValue);
        }
    }


    @Override
    public void deleteJob (UUID id){
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        jobRepository.delete(job);
    }





}
