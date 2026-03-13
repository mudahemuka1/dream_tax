package com.smartfarming.controller;

import com.smartfarming.entity.Recommendation;
import com.smartfarming.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(origins = "*")
public class RecommendationController {
    
    @Autowired
    private RecommendationService recommendationService;
    
    @GetMapping
    public ResponseEntity<List<Recommendation>> getAllRecommendations() {
        List<Recommendation> recommendations = recommendationService.getAllRecommendations();
        return ResponseEntity.ok(recommendations);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Recommendation> getRecommendationById(@PathVariable Long id) {
        return recommendationService.getRecommendationById(id)
            .map(recommendation -> ResponseEntity.ok(recommendation))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<Recommendation>> getRecommendationsByFarmer(@PathVariable Long farmerId) {
        List<Recommendation> recommendations = recommendationService.getRecommendationsByFarmer(farmerId);
        return ResponseEntity.ok(recommendations);
    }
    
    @GetMapping("/crop/{cropId}")
    public ResponseEntity<List<Recommendation>> getRecommendationsByCrop(@PathVariable Long cropId) {
        List<Recommendation> recommendations = recommendationService.getRecommendationsByCrop(cropId);
        return ResponseEntity.ok(recommendations);
    }
    
    @PostMapping("/crop-advice")
    public ResponseEntity<Recommendation> getCropRecommendation(@RequestBody Map<String, String> request) {
        try {
            Long farmerId = Long.parseLong(request.get("farmerId"));
            String soilType = request.get("soilType");
            String season = request.get("season");
            
            Recommendation recommendation = recommendationService.createCropRecommendation(farmerId, soilType, season);
            return ResponseEntity.status(HttpStatus.CREATED).body(recommendation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/disease-advice")
    public ResponseEntity<Recommendation> getDiseaseRecommendation(@RequestBody Map<String, String> request) {
        try {
            Long farmerId = Long.parseLong(request.get("farmerId"));
            Long cropId = Long.parseLong(request.get("cropId"));
            String symptoms = request.get("symptoms");
            
            Recommendation recommendation = recommendationService.createDiseaseRecommendation(farmerId, cropId, symptoms);
            return ResponseEntity.status(HttpStatus.CREATED).body(recommendation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/fertilizer-advice")
    public ResponseEntity<Recommendation> getFertilizerRecommendation(@RequestBody Map<String, String> request) {
        try {
            Long farmerId = Long.parseLong(request.get("farmerId"));
            Long cropId = Long.parseLong(request.get("cropId"));
            
            Recommendation recommendation = recommendationService.createFertilizerRecommendation(farmerId, cropId);
            return ResponseEntity.status(HttpStatus.CREATED).body(recommendation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Recommendation> createRecommendation(@RequestBody Recommendation recommendation) {
        Recommendation savedRecommendation = recommendationService.saveRecommendation(recommendation);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecommendation);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecommendation(@PathVariable Long id) {
        try {
            recommendationService.deleteRecommendation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
