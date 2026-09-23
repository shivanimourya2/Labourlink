package com.labourlink.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long contractorId;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Skill skillRequired;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String location;

    private Integer durationDays;

    @Column(nullable = false)
    private Double dailyRate;

    @Column(nullable = false)
    private Integer numberOfWorkersNeeded;

    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status = JobStatus.OPEN;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean urgentFlag = false;

    // Store contractor name for quick display
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

    public Long getContractorId() { return contractorId; }
    public void setContractorId(Long contractorId) { this.contractorId = contractorId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Skill getSkillRequired() { return skillRequired; }
    public void setSkillRequired(Skill skillRequired) { this.skillRequired = skillRequired; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Integer getDurationDays() { return durationDays; }
    public void setDurationDays(Integer durationDays) { this.durationDays = durationDays; }

    public Double getDailyRate() { return dailyRate; }
    public void setDailyRate(Double dailyRate) { this.dailyRate = dailyRate; }

    public Integer getNumberOfWorkersNeeded() { return numberOfWorkersNeeded; }
    public void setNumberOfWorkersNeeded(Integer numberOfWorkersNeeded) { this.numberOfWorkersNeeded = numberOfWorkersNeeded; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }

    public Boolean getUrgentFlag() { return urgentFlag; }
    public void setUrgentFlag(Boolean urgentFlag) { this.urgentFlag = urgentFlag; }

    public String getContractorName() { return contractorName; }
    public void setContractorName(String contractorName) { this.contractorName = contractorName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
