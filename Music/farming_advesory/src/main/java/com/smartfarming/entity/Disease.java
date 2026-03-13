package com.smartfarming.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Disease")
public class Disease {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Disease_ID")
    private Long diseaseId;
    
    @NotBlank(message = "Symptoms are required")
    @Column(name = "Symptoms", nullable = false, columnDefinition = "TEXT")
    private String symptoms;
    
    @Column(name = "Prevention_Method", columnDefinition = "TEXT")
    private String preventionMethod;
    
    @Size(max = 100, message = "Medicine type must not exceed 100 characters")
    @Column(name = "Medicine_Type")
    private String medicineType;
    
    @OneToMany(mappedBy = "disease", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recommendation> recommendations;
    
    // Constructors
    public Disease() {}
    
    public Disease(String symptoms, String preventionMethod, String medicineType) {
        this.symptoms = symptoms;
        this.preventionMethod = preventionMethod;
        this.medicineType = medicineType;
    }
    
    // Getters and Setters
    public Long getDiseaseId() {
        return diseaseId;
    }
    
    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }
    
    public String getSymptoms() {
        return symptoms;
    }
    
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
    
    public String getPreventionMethod() {
        return preventionMethod;
    }
    
    public void setPreventionMethod(String preventionMethod) {
        this.preventionMethod = preventionMethod;
    }
    
    public String getMedicineType() {
        return medicineType;
    }
    
    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }
    
    public List<Recommendation> getRecommendations() {
        return recommendations;
    }
    
    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }
}
