package com.labourlink.service;

import com.labourlink.model.*;
import com.labourlink.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final JobApplicationRepository applicationRepository;
    private final WorkHistoryRepository workHistoryRepository;
    private final UserRepository userRepository;
    private final BroadcastService broadcastService;
    private final NotificationService notificationService;

    public JobService(JobRepository jobRepository,
                      JobApplicationRepository applicationRepository,
                      WorkHistoryRepository workHistoryRepository,
                      UserRepository userRepository,
                      BroadcastService broadcastService,
                      NotificationService notificationService) {
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.workHistoryRepository = workHistoryRepository;
        this.userRepository = userRepository;
        this.broadcastService = broadcastService;
        this.notificationService = notificationService;
    }

    // ===== Job CRUD =====

    public Job createJob(Job job) {
        User contractor = userRepository.findById(job.getContractorId())
                .orElseThrow(() -> new RuntimeException("Contractor not found"));

        if (contractor.getRole() != Role.CONTRACTOR) {
            throw new RuntimeException("Only contractors can post jobs");
        }

        job.setContractorName(contractor.getName());
        job.setStatus(JobStatus.OPEN);
        Job saved = jobRepository.save(job);

        // Broadcast if urgent
        if (Boolean.TRUE.equals(saved.getUrgentFlag())) {
            broadcastService.broadcastUrgentJob(saved);
        }

        return saved;
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public List<Job> getOpenJobs(String skill, String location) {
        if (skill != null && location != null) {
            return jobRepository.findByStatusAndSkillRequiredAndLocation(
                JobStatus.OPEN, Skill.valueOf(skill.toUpperCase()), location);
        } else if (skill != null) {
            return jobRepository.findByStatusAndSkillRequired(
                JobStatus.OPEN, Skill.valueOf(skill.toUpperCase()));
        } else if (location != null) {
            return jobRepository.findByStatusAndLocation(JobStatus.OPEN, location);
        }
        return jobRepository.findByStatusOrderByUrgentFlagDescCreatedAtDesc(JobStatus.OPEN);
    }

    public List<Job> getContractorJobs(Long contractorId) {
        return jobRepository.findByContractorId(contractorId);
    }

    public List<Job> getContractorJobsByStatus(Long contractorId, JobStatus status) {
        return jobRepository.findByContractorIdAndStatus(contractorId, status);
    }

    // ===== Applications =====

    public JobApplication applyToJob(Long jobId, Long workerId) {
        Job job = getJobById(jobId);

        if (job.getStatus() != JobStatus.OPEN) {
            throw new RuntimeException("Job is no longer accepting applications");
        }

        if (applicationRepository.existsByJobIdAndWorkerId(jobId, workerId)) {
            throw new RuntimeException("Already applied to this job");
        }

        User worker = userRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        if (worker.getRole() != Role.WORKER) {
            throw new RuntimeException("Only workers can apply to jobs");
        }

        JobApplication application = new JobApplication();
        application.setJobId(jobId);
        application.setWorkerId(workerId);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setWorkerName(worker.getName());
        application.setWorkerPhone(worker.getPhone());
        application.setWorkerSkill(worker.getPrimarySkill());
        application.setWorkerRating(worker.getAverageRating());

        return applicationRepository.save(application);
    }

    public List<JobApplication> getApplicationsForJob(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    public List<JobApplication> getWorkerApplications(Long workerId) {
        return applicationRepository.findByWorkerId(workerId);
    }

    @Transactional
    public JobApplication selectWorker(Long applicationId) {
        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(ApplicationStatus.SELECTED);
        applicationRepository.save(application);

        // Notify worker
        notificationService.create(application.getWorkerId(),
                "🎉 You have been selected for Job #" + application.getJobId() + "! Get ready to start.",
                "JOB_SELECTED", application.getJobId());

        // Check if all needed workers selected, then move to ONGOING
        Job job = getJobById(application.getJobId());
        List<JobApplication> selected = applicationRepository.findByJobIdAndStatus(
                job.getId(), ApplicationStatus.SELECTED);

        if (selected.size() >= job.getNumberOfWorkersNeeded()) {
            job.setStatus(JobStatus.ONGOING);
            jobRepository.save(job);
        }

        return application;
    }

    public JobApplication rejectWorker(Long applicationId) {
        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(ApplicationStatus.REJECTED);
        return applicationRepository.save(application);
    }

    // ===== Job Completion =====

    @Transactional
    public Job completeJob(Long jobId, Long contractorId, String photos) {
        Job job = getJobById(jobId);

        if (!job.getContractorId().equals(contractorId)) {
            throw new RuntimeException("Only the job's contractor can mark it as complete");
        }

        if (job.getStatus() != JobStatus.ONGOING) {
            throw new RuntimeException("Job must be ongoing to be completed");
        }

        job.setStatus(JobStatus.COMPLETED);
        jobRepository.save(job);

        // Create work history entries for all selected workers
        User contractor = userRepository.findById(contractorId)
                .orElseThrow(() -> new RuntimeException("Contractor not found"));

        List<JobApplication> selectedApps = applicationRepository.findByJobIdAndStatus(
                jobId, ApplicationStatus.SELECTED);

        for (JobApplication app : selectedApps) {
            app.setStatus(ApplicationStatus.COMPLETED);
            applicationRepository.save(app);

            User worker = userRepository.findById(app.getWorkerId())
                    .orElseThrow(() -> new RuntimeException("Worker not found"));

            WorkHistory wh = new WorkHistory();
            wh.setJobId(jobId);
            wh.setWorkerId(app.getWorkerId());
            wh.setContractorId(contractorId);
            wh.setJobTitle(job.getTitle());
            wh.setWorkerName(worker.getName());
            wh.setContractorName(contractor.getName());
            wh.setSkillUsed(job.getSkillRequired());
            wh.setLocation(job.getLocation());
            wh.setStartDate(job.getStartDate());
            wh.setEndDate(LocalDate.now());
            wh.setPhotos(photos);

            workHistoryRepository.save(wh);

            // Notify worker
            notificationService.create(worker.getId(),
                    "✅ Job \"" + job.getTitle() + "\" is now completed! Please rate your contractor.",
                    "JOB_COMPLETED", jobId);
        }

        return job;
    }
}
