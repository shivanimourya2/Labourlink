package com.labourlink.repository;

import com.labourlink.model.FavoriteWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteWorkerRepository extends JpaRepository<FavoriteWorker, Long> {
    List<FavoriteWorker> findByContractorId(Long contractorId);
    Optional<FavoriteWorker> findByContractorIdAndWorkerId(Long contractorId, Long workerId);
    boolean existsByContractorIdAndWorkerId(Long contractorId, Long workerId);
}
