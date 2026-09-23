package com.labourlink.controller;

import com.labourlink.dto.ApiResponse;
import com.labourlink.model.*;
import com.labourlink.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createJob(@RequestBody Job job) {
        try {
            Job created = jobService.createJob(job);
            return ResponseEntity.ok(ApiResponse.ok("Job posted successfully", created));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getJob(@PathVariable Long id) {
        try {
            Job job = jobService.getJobById(id);
            return ResponseEntity.ok(ApiResponse.ok("Job found", job));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/open")
    public ResponseEntity<ApiResponse> getOpenJobs(
            @RequestParam(required = false) String skill,
            @RequestParam(required = false) String location) {
        try {
            List<Job> jobs = jobService.getOpenJobs(skill, location);
            return ResponseEntity.ok(ApiResponse.ok("Jobs found", jobs));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/contractor/{contractorId}")
    public ResponseEntity<ApiResponse> getContractorJobs(@PathVariable Long contractorId) {
        try {
            List<Job> jobs = jobService.getContractorJobs(contractorId);
            return ResponseEntity.ok(ApiResponse.ok("Jobs found", jobs));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // ===== Applications =====

    @PostMapping("/{jobId}/apply")
    public ResponseEntity<ApiResponse> applyToJob(
            @PathVariable Long jobId,
            @RequestParam Long workerId) {
        try {
            JobApplication application = jobService.applyToJob(jobId, workerId);
            return ResponseEntity.ok(ApiResponse.ok("Applied successfully", application));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{jobId}/applications")
    public ResponseEntity<ApiResponse> getApplications(@PathVariable Long jobId) {
        try {
            List<JobApplication> apps = jobService.getApplicationsForJob(jobId);
            return ResponseEntity.ok(ApiResponse.ok("Applications found", apps));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/applications/worker/{workerId}")
    public ResponseEntity<ApiResponse> getWorkerApplications(@PathVariable Long workerId) {
        try {
            List<JobApplication> apps = jobService.getWorkerApplications(workerId);
            return ResponseEntity.ok(ApiResponse.ok("Applications found", apps));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/applications/{applicationId}/select")
    public ResponseEntity<ApiResponse> selectWorker(@PathVariable Long applicationId) {
        try {
            JobApplication app = jobService.selectWorker(applicationId);
            return ResponseEntity.ok(ApiResponse.ok("Worker selected", app));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/applications/{applicationId}/reject")
    public ResponseEntity<ApiResponse> rejectWorker(@PathVariable Long applicationId) {
        try {
            JobApplication app = jobService.rejectWorker(applicationId);
            return ResponseEntity.ok(ApiResponse.ok("Worker rejected", app));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/{jobId}/complete")
    public ResponseEntity<ApiResponse> completeJob(
            @PathVariable Long jobId,
            @RequestParam Long contractorId,
            @RequestParam(required = false) String photos) {
        try {
            Job job = jobService.completeJob(jobId, contractorId, photos);
            return ResponseEntity.ok(ApiResponse.ok("Job marked as completed", job));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
