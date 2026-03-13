package com.smartfarming.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Fertilizer")
public class Fertilizer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Fertilizer_ID")
    private Long fertilizerId;
    
    @NotBlank(message = "Nutrient content is required")
    @Size(max = 150, message = "Nutrient content must not exceed 150 characters")
    @Column(name = "Nutrient_Content", nullable = false)
    private String nutrientContent;
    
    @NotBlank(message = "Recommended dose is required")
    @Size(max = 100, message = "Recommended dose must not exceed 100 characters")
    @Column(name = "Recommended_Dose", nullable = false)
    private String recommendedDose;
    
    @OneToMany(mappedBy = "fertilizer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recommendation> recommendations;
    
    // Constructors
    public Fertilizer() {}
    
    public Fertilizer(String nutrientContent, String recommendedDose) {
        this.nutrientContent = nutrientContent;
        this.recommendedDose = recommendedDose;
    }
    
    // Getters and Setters
    public Long getFertilizerId() {
        return fertilizerId;
    }
    
    public void setFertilizerId(Long fertilizerId) {
        this.fertilizerId = fertilizerId;
    }
    
    public String getNutrientContent() {
        return nutrientContent;
    }
    
    public void setNutrientContent(String nutrientContent) {
        this.nutrientContent = nutrientContent;
    }
    
    public String getRecommendedDose() {
        return recommendedDose;
    }
    
    public void setRecommendedDose(String recommendedDose) {
        this.recommendedDose = recommendedDose;
    }
    
    public List<Recommendation> getRecommendations() {
        return recommendations;
    }
    
    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }
}
