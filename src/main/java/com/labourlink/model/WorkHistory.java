package com.labourlink.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_history")
public class WorkHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long jobId;

    @Column(nullable = false)
    private Long workerId;

    @Column(nullable = false)
    private Long contractorId;

    private String jobTitle;
    private String workerName;
    private String contractorName;

    @Enumerated(EnumType.STRING)
    private Skill skillUsed;

    private String location;
    private LocalDate startDate;
    private LocalDate endDate;

    // Rating given by contractor to worker (1-5)
    private Integer workerRating;
    private String workerNote;

    // Rating given by worker to contractor (1-5)
    private Integer contractorRating;
    private String contractorNote;

    private Boolean workerRated = false;
    private Boolean contractorRated = false;

    @Column(columnDefinition = "TEXT")
    private String photos; // comma-separated URLs

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // ===== Getters and Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public Long getWorkerId() { return workerId; }
    public void setWorkerId(Long workerId) { this.workerId = workerId; }

    public Long getContractorId() { return contractorId; }
    public void setContractorId(Long contractorId) { this.contractorId = contractorId; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getWorkerName() { return workerName; }
    public void setWorkerName(String workerName) { this.workerName = workerName; }

    public String getContractorName() { return contractorName; }
    public void setContractorName(String contractorName) { this.contractorName = contractorName; }

    public Skill getSkillUsed() { return skillUsed; }
    public void setSkillUsed(Skill skillUsed) { this.skillUsed = skillUsed; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Integer getWorkerRating() { return workerRating; }
    public void setWorkerRating(Integer workerRating) { this.workerRating = workerRating; }

    public String getWorkerNote() { return workerNote; }
    public void setWorkerNote(String workerNote) { this.workerNote = workerNote; }

    public Integer getContractorRating() { return contractorRating; }
    public void setContractorRating(Integer contractorRating) { this.contractorRating = contractorRating; }

    public String getContractorNote() { return contractorNote; }
    public void setContractorNote(String contractorNote) { this.contractorNote = contractorNote; }

    public Boolean getWorkerRated() { return workerRated; }
    public void setWorkerRated(Boolean workerRated) { this.workerRated = workerRated; }

    public Boolean getContractorRated() { return contractorRated; }
    public void setContractorRated(Boolean contractorRated) { this.contractorRated = contractorRated; }

    public String getPhotos() { return photos; }
    public void setPhotos(String photos) { this.photos = photos; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
