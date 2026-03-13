package com.smartfarming.service;

import com.smartfarming.entity.Fertilizer;
import com.smartfarming.repository.FertilizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FertilizerService {
    
    @Autowired
    private FertilizerRepository fertilizerRepository;
    
    public List<Fertilizer> getAllFertilizers() {
        return fertilizerRepository.findAll();
    }
    
    public Optional<Fertilizer> getFertilizerById(Long id) {
        return fertilizerRepository.findById(id);
    }
    
    public Fertilizer saveFertilizer(Fertilizer fertilizer) {
        return fertilizerRepository.save(fertilizer);
    }
    
    public Fertilizer updateFertilizer(Long id, Fertilizer fertilizerDetails) {
        return fertilizerRepository.findById(id)
            .map(fertilizer -> {
                fertilizer.setNutrientContent(fertilizerDetails.getNutrientContent());
                fertilizer.setRecommendedDose(fertilizerDetails.getRecommendedDose());
                return fertilizerRepository.save(fertilizer);
            })
            .orElseThrow(() -> new RuntimeException("Fertilizer not found with id: " + id));
    }
    
    public void deleteFertilizer(Long id) {
        if (!fertilizerRepository.existsById(id)) {
            throw new RuntimeException("Fertilizer not found with id: " + id);
        }
        fertilizerRepository.deleteById(id);
    }
    
    public List<Fertilizer> searchFertilizersByNutrient(String nutrient) {
        return fertilizerRepository.findByNutrientContentContaining(nutrient);
    }
    
    public List<Fertilizer> getFertilizersByDose(String dose) {
        return fertilizerRepository.findByRecommendedDoseContaining(dose);
    }
}
