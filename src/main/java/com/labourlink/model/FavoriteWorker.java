package com.labourlink.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorite_workers")
public class FavoriteWorker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long contractorId;

    @Column(nullable = false)
    private Long workerId;

    private String workerName;

    @Enumerated(EnumType.STRING)
    private Skill workerSkill;

    private Double workerRating;

    @Column(nullable = false, updatable = false)
    private LocalDateTime addedAt;

    @PrePersist
    protected void onCreate() {
        addedAt = LocalDateTime.now();
    }

    // ===== Getters and Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getContractorId() { return contractorId; }
    public void setContractorId(Long contractorId) { this.contractorId = contractorId; }

    public Long getWorkerId() { return workerId; }
    public void setWorkerId(Long workerId) { this.workerId = workerId; }

    public String getWorkerName() { return workerName; }
    public void setWorkerName(String workerName) { this.workerName = workerName; }

    public Skill getWorkerSkill() { return workerSkill; }
    public void setWorkerSkill(Skill workerSkill) { this.workerSkill = workerSkill; }

    public Double getWorkerRating() { return workerRating; }
    public void setWorkerRating(Double workerRating) { this.workerRating = workerRating; }

    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }
}
