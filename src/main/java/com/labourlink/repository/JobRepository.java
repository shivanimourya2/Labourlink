package com.labourlink.repository;

import com.labourlink.model.Job;
import com.labourlink.model.JobStatus;
import com.labourlink.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByContractorId(Long contractorId);
    List<Job> findByStatus(JobStatus status);
    List<Job> findByStatusAndSkillRequired(JobStatus status, Skill skill);
    List<Job> findByStatusAndLocation(JobStatus status, String location);
    List<Job> findByStatusAndSkillRequiredAndLocation(JobStatus status, Skill skill, String location);
    List<Job> findByContractorIdAndStatus(Long contractorId, JobStatus status);
    List<Job> findByStatusOrderByUrgentFlagDescCreatedAtDesc(JobStatus status);
}
