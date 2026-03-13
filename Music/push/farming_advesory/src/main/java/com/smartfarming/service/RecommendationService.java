package com.smartfarming.service;

import com.smartfarming.entity.*;
import com.smartfarming.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RecommendationService {
    
    @Autowired
    private RecommendationRepository recommendationRepository;
    
    @Autowired
    private FarmerRepository farmerRepository;
    
    @Autowired
    private CropRepository cropRepository;
    
    @Autowired
    private FertilizerRepository fertilizerRepository;
    
    @Autowired
    private DiseaseRepository diseaseRepository;
    
    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
    }
    
    public Optional<Recommendation> getRecommendationById(Long id) {
        return recommendationRepository.findById(id);
    }
    
    public List<Recommendation> getRecommendationsByFarmer(Long farmerId) {
        return recommendationRepository.findByFarmerFarmerId(farmerId);
    }
    
    public List<Recommendation> getRecommendationsByCrop(Long cropId) {
        return recommendationRepository.findByCropCropId(cropId);
    }
    
    public Recommendation createCropRecommendation(Long farmerId, String soilType, String season) {
        Farmer farmer = farmerRepository.findById(farmerId)
            .orElseThrow(() -> new RuntimeException("Farmer not found with id: " + farmerId));
        
        List<Crop> recommendedCrops = cropRepository.findBySoilAndSeason(soilType, season);
        
        if (recommendedCrops.isEmpty()) {
            throw new RuntimeException("No suitable crops found for soil: " + soilType + " and season: " + season);
        }
        
        Crop recommendedCrop = recommendedCrops.get(0);
        
        String adviceDetails = String.format(
            "Based on your %s soil type and %s season, we recommend planting %s. " +
            "This crop is well-suited for your conditions and should yield good results.",
            soilType, season, recommendedCrop.getCropName()
        );
        
        Recommendation recommendation = new Recommendation();
        recommendation.setFarmer(farmer);
        recommendation.setCrop(recommendedCrop);
        recommendation.setAdviceDetails(adviceDetails);
        recommendation.setDate(LocalDate.now());
        
        return recommendationRepository.save(recommendation);
    }
    
    public Recommendation createDiseaseRecommendation(Long farmerId, Long cropId, String symptoms) {
        Farmer farmer = farmerRepository.findById(farmerId)
            .orElseThrow(() -> new RuntimeException("Farmer not found with id: " + farmerId));
        
        Crop crop = cropRepository.findById(cropId)
            .orElseThrow(() -> new RuntimeException("Crop not found with id: " + cropId));
        
        List<Disease> matchingDiseases = diseaseRepository.findBySymptomsContaining(symptoms);
        
        String adviceDetails;
        Disease disease = null;
        
        if (!matchingDiseases.isEmpty()) {
            disease = matchingDiseases.get(0);
            adviceDetails = String.format(
                "Based on the symptoms '%s' for %s, this appears to be %s. " +
                "Recommended treatment: %s. Prevention: %s",
                symptoms, crop.getCropName(), 
                disease.getMedicineType() != null ? disease.getMedicineType() : "appropriate treatment",
                disease.getMedicineType() != null ? disease.getMedicineType() : "consult local agricultural expert",
                disease.getPreventionMethod() != null ? disease.getPreventionMethod() : "follow good agricultural practices"
            );
        } else {
            adviceDetails = String.format(
                "For the symptoms '%s' on %s, we recommend consulting with a local agricultural expert " +
                "for proper diagnosis and treatment. Please contact your extension officer.",
                symptoms, crop.getCropName()
            );
        }
        
        Recommendation recommendation = new Recommendation();
        recommendation.setFarmer(farmer);
        recommendation.setCrop(crop);
        recommendation.setDisease(disease);
        recommendation.setAdviceDetails(adviceDetails);
        recommendation.setDate(LocalDate.now());
        
        return recommendationRepository.save(recommendation);
    }
    
    public Recommendation createFertilizerRecommendation(Long farmerId, Long cropId) {
        Farmer farmer = farmerRepository.findById(farmerId)
            .orElseThrow(() -> new RuntimeException("Farmer not found with id: " + farmerId));
        
        Crop crop = cropRepository.findById(cropId)
            .orElseThrow(() -> new RuntimeException("Crop not found with id: " + cropId));
        
        List<Fertilizer> fertilizers = fertilizerRepository.findAll();
        
        String adviceDetails;
        Fertilizer fertilizer = null;
        
        if (!fertilizers.isEmpty()) {
            fertilizer = fertilizers.get(0);
            adviceDetails = String.format(
                "For %s, we recommend using %s with nutrient content %s. " +
                "Apply at the recommended dose: %s for optimal growth and yield.",
                crop.getCropName(), fertilizer.getNutrientContent(), 
                fertilizer.getNutrientContent(), fertilizer.getRecommendedDose()
            );
        } else {
            adviceDetails = String.format(
                "For %s, please consult with your local agro-dealer for appropriate fertilizer recommendations " +
                "based on your soil conditions and crop requirements.",
                crop.getCropName()
            );
        }
        
        Recommendation recommendation = new Recommendation();
        recommendation.setFarmer(farmer);
        recommendation.setCrop(crop);
        recommendation.setFertilizer(fertilizer);
        recommendation.setAdviceDetails(adviceDetails);
        recommendation.setDate(LocalDate.now());
        
        return recommendationRepository.save(recommendation);
    }
    
    public Recommendation saveRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }
    
    public void deleteRecommendation(Long id) {
        if (!recommendationRepository.existsById(id)) {
            throw new RuntimeException("Recommendation not found with id: " + id);
        }
        recommendationRepository.deleteById(id);
    }
}
