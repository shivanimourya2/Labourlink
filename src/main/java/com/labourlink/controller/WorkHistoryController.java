package com.labourlink.controller;

import com.labourlink.dto.*;
import com.labourlink.model.*;
import com.labourlink.service.WorkHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-history")
@CrossOrigin(origins = "*")
public class WorkHistoryController {

    private final WorkHistoryService workHistoryService;

    public WorkHistoryController(WorkHistoryService workHistoryService) {
        this.workHistoryService = workHistoryService;
    }

    @GetMapping("/worker/{workerId}")
    public ResponseEntity<ApiResponse> getWorkerHistory(@PathVariable Long workerId) {
        try {
            List<WorkHistory> history = workHistoryService.getWorkerHistory(workerId);
            return ResponseEntity.ok(ApiResponse.ok("Work history found", history));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/contractor/{contractorId}")
    public ResponseEntity<ApiResponse> getContractorHistory(@PathVariable Long contractorId) {
        try {
            List<WorkHistory> history = workHistoryService.getContractorHistory(contractorId);
            return ResponseEntity.ok(ApiResponse.ok("Work history found", history));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getWorkHistory(@PathVariable Long id) {
        try {
            WorkHistory wh = workHistoryService.getById(id);
            return ResponseEntity.ok(ApiResponse.ok("Work history found", wh));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // ===== Ratings =====

    @PostMapping("/rate-worker")
    public ResponseEntity<ApiResponse> rateWorker(@RequestBody RatingRequest request) {
        try {
            WorkHistory wh = workHistoryService.rateWorker(request);
            return ResponseEntity.ok(ApiResponse.ok("Worker rated successfully", wh));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/rate-contractor")
    public ResponseEntity<ApiResponse> rateContractor(@RequestBody RatingRequest request) {
        try {
            WorkHistory wh = workHistoryService.rateContractor(request);
            return ResponseEntity.ok(ApiResponse.ok("Contractor rated successfully", wh));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // ===== Endorsements =====

    @PostMapping("/endorse")
    public ResponseEntity<ApiResponse> addEndorsement(@RequestBody EndorsementRequest request) {
        try {
            Endorsement endorsement = workHistoryService.addEndorsement(request);
            return ResponseEntity.ok(ApiResponse.ok("Endorsement added", endorsement));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/endorsements/worker/{workerId}")
    public ResponseEntity<ApiResponse> getWorkerEndorsements(@PathVariable Long workerId) {
        try {
            List<Endorsement> endorsements = workHistoryService.getWorkerEndorsements(workerId);
            return ResponseEntity.ok(ApiResponse.ok("Endorsements found", endorsements));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/endorsements/{workHistoryId}")
    public ResponseEntity<ApiResponse> getWorkHistoryEndorsements(@PathVariable Long workHistoryId) {
        try {
            List<Endorsement> endorsements = workHistoryService.getWorkHistoryEndorsements(workHistoryId);
            return ResponseEntity.ok(ApiResponse.ok("Endorsements found", endorsements));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
