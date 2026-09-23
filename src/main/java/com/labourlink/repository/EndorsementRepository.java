package com.labourlink.repository;

import com.labourlink.model.Endorsement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Long> {
    List<Endorsement> findByWorkerId(Long workerId);
    List<Endorsement> findByWorkHistoryId(Long workHistoryId);
    List<Endorsement> findByWorkerIdOrderByCreatedAtDesc(Long workerId);
}
