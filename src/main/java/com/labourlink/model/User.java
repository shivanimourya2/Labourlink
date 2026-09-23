package com.labourlink.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String password;

    private String city;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Worker-specific fields
    @Enumerated(EnumType.STRING)
    private Skill primarySkill;

    @Column(columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double averageRating = 0.0;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer totalRatings = 0;

    // Contractor-specific fields
    private String companyName;

    @Column(columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double contractorRating = 0.0;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer contractorTotalRatings = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // ===== Getters and Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Skill getPrimarySkill() { return primarySkill; }
    public void setPrimarySkill(Skill primarySkill) { this.primarySkill = primarySkill; }

    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

    public Integer getTotalRatings() { return totalRatings; }
    public void setTotalRatings(Integer totalRatings) { this.totalRatings = totalRatings; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public Double getContractorRating() { return contractorRating; }
    public void setContractorRating(Double contractorRating) { this.contractorRating = contractorRating; }

    public Integer getContractorTotalRatings() { return contractorTotalRatings; }
    public void setContractorTotalRatings(Integer contractorTotalRatings) { this.contractorTotalRatings = contractorTotalRatings; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
