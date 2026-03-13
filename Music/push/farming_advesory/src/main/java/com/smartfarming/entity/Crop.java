package com.smartfarming.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Crop")
public class Crop {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Crop_ID")
    private Long cropId;
    
    @NotBlank(message = "Crop name is required")
    @Size(max = 100, message = "Crop name must not exceed 100 characters")
    @Column(name = "Crop_Name", nullable = false, unique = true)
    private String cropName;
    
    @NotBlank(message = "Ideal soil is required")
    @Size(max = 100, message = "Ideal soil must not exceed 100 characters")
    @Column(name = "Ideal_Soil", nullable = false)
    private String idealSoil;
    
    @NotBlank(message = "Ideal season is required")
    @Size(max = 50, message = "Ideal season must not exceed 50 characters")
    @Column(name = "Ideal_Season", nullable = false)
    private String idealSeason;
    
    @OneToMany(mappedBy = "crop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recommendation> recommendations;
    
    // Constructors
    public Crop() {}
    
    public Crop(String cropName, String idealSoil, String idealSeason) {
        this.cropName = cropName;
        this.idealSoil = idealSoil;
        this.idealSeason = idealSeason;
    }
    
    // Getters and Setters
    public Long getCropId() {
        return cropId;
    }
    
    public void setCropId(Long cropId) {
        this.cropId = cropId;
    }
    
    public String getCropName() {
        return cropName;
    }
    
    public void setCropName(String cropName) {
        this.cropName = cropName;
    }
    
    public String getIdealSoil() {
        return idealSoil;
    }
    
    public void setIdealSoil(String idealSoil) {
        this.idealSoil = idealSoil;
    }
    
    public String getIdealSeason() {
        return idealSeason;
    }
    
    public void setIdealSeason(String idealSeason) {
        this.idealSeason = idealSeason;
    }
    
    public List<Recommendation> getRecommendations() {
        return recommendations;
    }
    
    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }
}
