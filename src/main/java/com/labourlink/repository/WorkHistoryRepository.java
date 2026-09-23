package com.labourlink.repository;

import com.labourlink.model.WorkHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkHistoryRepository extends JpaRepository<WorkHistory, Long> {
    List<WorkHistory> findByWorkerId(Long workerId);
    List<WorkHistory> findByContractorId(Long contractorId);
    List<WorkHistory> findByJobId(Long jobId);
    List<WorkHistory> findByWorkerIdOrderByCreatedAtDesc(Long workerId);
    List<WorkHistory> findByContractorIdOrderByCreatedAtDesc(Long contractorId);
}
