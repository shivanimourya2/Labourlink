package com.labourlink.service;

import com.labourlink.dto.EndorsementRequest;
import com.labourlink.dto.RatingRequest;
import com.labourlink.model.*;
import com.labourlink.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkHistoryService {

    private final WorkHistoryRepository workHistoryRepository;
    private final EndorsementRepository endorsementRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public WorkHistoryService(WorkHistoryRepository workHistoryRepository,
                              EndorsementRepository endorsementRepository,
                              UserService userService,
                              UserRepository userRepository) {
        this.workHistoryRepository = workHistoryRepository;
        this.endorsementRepository = endorsementRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public List<WorkHistory> getWorkerHistory(Long workerId) {
        return workHistoryRepository.findByWorkerIdOrderByCreatedAtDesc(workerId);
    }

    public List<WorkHistory> getContractorHistory(Long contractorId) {
        return workHistoryRepository.findByContractorIdOrderByCreatedAtDesc(contractorId);
    }

    public WorkHistory getById(Long id) {
        return workHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work history not found"));
    }

    /**
     * Contractor rates a worker (workerRating on WorkHistory).
     * Also updates the worker's running average rating.
     */
    public WorkHistory rateWorker(RatingRequest request) {
        WorkHistory wh = getById(request.getWorkHistoryId());

        if (wh.getContractorRated()) {
            throw new RuntimeException("Worker has already been rated for this job");
        }

        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        // Verify the rater is the contractor
        if (!wh.getContractorId().equals(request.getRaterId())) {
            throw new RuntimeException("Only the job's contractor can rate the worker");
        }

        wh.setWorkerRating(request.getRating());
        wh.setWorkerNote(request.getNote());
        wh.setContractorRated(true);
        workHistoryRepository.save(wh);

        // Update worker's average rating
        userService.updateWorkerRating(wh.getWorkerId(), request.getRating());

        return wh;
    }

    /**
     * Worker rates a contractor (contractorRating on WorkHistory).
     * Also updates the contractor's running average rating.
     */
    public WorkHistory rateContractor(RatingRequest request) {
        WorkHistory wh = getById(request.getWorkHistoryId());

        if (wh.getWorkerRated()) {
            throw new RuntimeException("Contractor has already been rated for this job");
        }

        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        // Verify the rater is the worker
        if (!wh.getWorkerId().equals(request.getRaterId())) {
            throw new RuntimeException("Only the job's worker can rate the contractor");
        }

        wh.setContractorRating(request.getRating());
        wh.setContractorNote(request.getNote());
        wh.setWorkerRated(true);
        workHistoryRepository.save(wh);

        // Update contractor's average rating
        userService.updateContractorRating(wh.getContractorId(), request.getRating());

        return wh;
    }

    // ===== Endorsements =====

    public Endorsement addEndorsement(EndorsementRequest request) {
        WorkHistory wh = getById(request.getWorkHistoryId());

        // Verify the endorser is the contractor for this work
        if (!wh.getContractorId().equals(request.getContractorId())) {
            throw new RuntimeException("Only the job's contractor can add endorsements");
        }

        User contractor = userService.getUserById(request.getContractorId());

        Endorsement endorsement = new Endorsement();
        endorsement.setWorkHistoryId(request.getWorkHistoryId());
        endorsement.setWorkerId(wh.getWorkerId());
        endorsement.setCreatedBy(request.getContractorId());
        endorsement.setSkillTag(request.getSkillTag());
        endorsement.setContractorName(contractor.getName());

        return endorsementRepository.save(endorsement);
    }

    public List<Endorsement> getWorkerEndorsements(Long workerId) {
        return endorsementRepository.findByWorkerIdOrderByCreatedAtDesc(workerId);
    }

    public List<Endorsement> getWorkHistoryEndorsements(Long workHistoryId) {
        return endorsementRepository.findByWorkHistoryId(workHistoryId);
    }
}
