package com.smartfarming.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Agronomist")
public class Agronomist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Agronomist_ID")
    private Long agronomistId;
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Column(name = "Name", nullable = false)
    private String name;
    
    @NotBlank(message = "Qualification is required")
    @Size(max = 150, message = "Qualification must not exceed 150 characters")
    @Column(name = "Qualification", nullable = false)
    private String qualification;
    
    @NotBlank(message = "Phone number is required")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Column(name = "Phone", nullable = false)
    private String phone;
    
    // Constructors
    public Agronomist() {}
    
    public Agronomist(String name, String qualification, String phone) {
        this.name = name;
        this.qualification = qualification;
        this.phone = phone;
    }
    
    // Getters and Setters
    public Long getAgronomistId() {
        return agronomistId;
    }
    
    public void setAgronomistId(Long agronomistId) {
        this.agronomistId = agronomistId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getQualification() {
        return qualification;
    }
    
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
