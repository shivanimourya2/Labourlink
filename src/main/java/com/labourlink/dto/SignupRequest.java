package com.labourlink.dto;

public class SignupRequest {
    private String name;
    private String phone;
    private String password;
    private String city;
    private String role; // WORKER or CONTRACTOR
    private String primarySkill; // for workers
    private String companyName; // for contractors

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPrimarySkill() { return primarySkill; }
    public void setPrimarySkill(String primarySkill) { this.primarySkill = primarySkill; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}
