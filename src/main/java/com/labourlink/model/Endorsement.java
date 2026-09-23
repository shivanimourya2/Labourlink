package com.labourlink.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "endorsements")
public class Endorsement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long workHistoryId;

    @Column(nullable = false)
    private Long workerId;

    @Column(nullable = false)
    private Long createdBy; // contractorId

    @Column(nullable = false)
    private String skillTag;

    private String contractorName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // ===== Getters and Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getWorkHistoryId() { return workHistoryId; }
    public void setWorkHistoryId(Long workHistoryId) { this.workHistoryId = workHistoryId; }

    public Long getWorkerId() { return workerId; }
    public void setWorkerId(Long workerId) { this.workerId = workerId; }

    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }

    public String getSkillTag() { return skillTag; }
    public void setSkillTag(String skillTag) { this.skillTag = skillTag; }

    public String getContractorName() { return contractorName; }
    public void setContractorName(String contractorName) { this.contractorName = contractorName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
