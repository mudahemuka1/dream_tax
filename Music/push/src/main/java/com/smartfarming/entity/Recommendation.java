package com.smartfarming.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Recommendation")
public class Recommendation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Recommendation_ID")
    private Long recommendationId;
    
    @NotNull(message = "Farmer is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Farmer_ID", nullable = false, foreignKey = @ForeignKey(name = "fk_rec_farmer"))
    private Farmer farmer;
    
    @NotNull(message = "Crop is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Crop_ID", nullable = false, foreignKey = @ForeignKey(name = "fk_rec_crop"))
    private Crop crop;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Fertilizer_ID", foreignKey = @ForeignKey(name = "fk_rec_fertilizer"))
    private Fertilizer fertilizer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Disease_ID", foreignKey = @ForeignKey(name = "fk_rec_disease"))
    private Disease disease;
    
    @NotBlank(message = "Advice details are required")
    @Column(name = "Advice_Details", nullable = false, columnDefinition = "TEXT")
    private String adviceDetails;
    
    @NotNull(message = "Date is required")
    @Column(name = "Date", nullable = false)
    private LocalDate date;
    
    @Column(name = "Created_At", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = LocalDate.now();
        }
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public Recommendation() {}
    
    public Recommendation(Farmer farmer, Crop crop, String adviceDetails) {
        this.farmer = farmer;
        this.crop = crop;
        this.adviceDetails = adviceDetails;
        this.date = LocalDate.now();
    }
    
    public Recommendation(Farmer farmer, Crop crop, Fertilizer fertilizer, Disease disease, String adviceDetails) {
        this.farmer = farmer;
        this.crop = crop;
        this.fertilizer = fertilizer;
        this.disease = disease;
        this.adviceDetails = adviceDetails;
        this.date = LocalDate.now();
    }
    
    // Getters and Setters
    public Long getRecommendationId() {
        return recommendationId;
    }
    
    public void setRecommendationId(Long recommendationId) {
        this.recommendationId = recommendationId;
    }
    
    public Farmer getFarmer() {
        return farmer;
    }
    
    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }
    
    public Crop getCrop() {
        return crop;
    }
    
    public void setCrop(Crop crop) {
        this.crop = crop;
    }
    
    public Fertilizer getFertilizer() {
        return fertilizer;
    }
    
    public void setFertilizer(Fertilizer fertilizer) {
        this.fertilizer = fertilizer;
    }
    
    public Disease getDisease() {
        return disease;
    }
    
    public void setDisease(Disease disease) {
        this.disease = disease;
    }
    
    public String getAdviceDetails() {
        return adviceDetails;
    }
    
    public void setAdviceDetails(String adviceDetails) {
        this.adviceDetails = adviceDetails;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
