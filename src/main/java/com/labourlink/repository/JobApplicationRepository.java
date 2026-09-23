package com.labourlink.repository;

import com.labourlink.model.JobApplication;
import com.labourlink.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByJobId(Long jobId);
    List<JobApplication> findByWorkerId(Long workerId);
    List<JobApplication> findByJobIdAndStatus(Long jobId, ApplicationStatus status);
    Optional<JobApplication> findByJobIdAndWorkerId(Long jobId, Long workerId);
    boolean existsByJobIdAndWorkerId(Long jobId, Long workerId);
    List<JobApplication> findByWorkerIdAndStatus(Long workerId, ApplicationStatus status);
}
